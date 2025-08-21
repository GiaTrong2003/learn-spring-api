package com.giatrong.learning.learnspringapi.service;

import com.giatrong.learning.learnspringapi.dto.response.HealthResponse;
import com.giatrong.learning.learnspringapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthService {

    private final DataSource dataSource;
    private final UserRepository userRepository;

    @Value("${spring.application.name:learn-spring-api}")
    private String applicationName;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @Value("${application.version:1.0.0}")
    private String applicationVersion;

    public HealthResponse getHealthStatus() {
        log.debug("Checking application health status");

        try {
            Map<String, HealthResponse.ComponentHealth> components = new HashMap<>();
            
            // Check database health
            components.put("database", checkDatabaseHealth());
            
            // Check application health
            components.put("application", checkApplicationHealth());

            // Get system information
            HealthResponse.SystemInfo systemInfo = getSystemInfo();

            // Calculate uptime
            long uptime = ManagementFactory.getRuntimeMXBean().getUptime();

            return HealthResponse.builder()
                    .status("UP")
                    .version(applicationVersion)
                    .timestamp(LocalDateTime.now())
                    .uptime(uptime)
                    .environment(activeProfile)
                    .components(components)
                    .systemInfo(systemInfo)
                    .build();

        } catch (Exception e) {
            log.error("Health check failed", e);
            return HealthResponse.builder()
                    .status("DOWN")
                    .version(applicationVersion)
                    .timestamp(LocalDateTime.now())
                    .environment(activeProfile)
                    .components(Map.of("error", HealthResponse.ComponentHealth.builder()
                            .status("DOWN")
                            .details(Map.of("error", e.getMessage()))
                            .build()))
                    .build();
        }
    }

    private HealthResponse.ComponentHealth checkDatabaseHealth() {
        try {
            // Test database connection
            try (Connection connection = dataSource.getConnection()) {
                boolean isValid = connection.isValid(5); // 5 seconds timeout
                
                if (isValid) {
                    // Try to count users to ensure database is working
                    long userCount = userRepository.count();
                    
                    Map<String, Object> details = new HashMap<>();
                    details.put("database", "MySQL");
                    details.put("status", "Connected");
                    details.put("userCount", userCount);
                    details.put("validationQuery", "SELECT 1");
                    
                    return HealthResponse.ComponentHealth.builder()
                            .status("UP")
                            .details(details)
                            .build();
                } else {
                    return HealthResponse.ComponentHealth.builder()
                            .status("DOWN")
                            .details(Map.of("error", "Database connection is not valid"))
                            .build();
                }
            }
        } catch (SQLException e) {
            log.error("Database health check failed", e);
            return HealthResponse.ComponentHealth.builder()
                    .status("DOWN")
                    .details(Map.of("error", "Database connection failed: " + e.getMessage()))
                    .build();
        }
    }

    private HealthResponse.ComponentHealth checkApplicationHealth() {
        Map<String, Object> details = new HashMap<>();
        details.put("name", applicationName);
        details.put("version", applicationVersion);
        details.put("profile", activeProfile);
        details.put("javaVersion", System.getProperty("java.version"));
        
        return HealthResponse.ComponentHealth.builder()
                .status("UP")
                .details(details)
                .build();
    }

    private HealthResponse.SystemInfo getSystemInfo() {
        Runtime runtime = Runtime.getRuntime();
        
        long totalMemory = runtime.totalMemory() / (1024 * 1024); // Convert to MB
        long freeMemory = runtime.freeMemory() / (1024 * 1024);   // Convert to MB
        long usedMemory = totalMemory - freeMemory;
        double memoryUsage = ((double) usedMemory / totalMemory) * 100;

        return HealthResponse.SystemInfo.builder()
                .javaVersion(System.getProperty("java.version"))
                .osName(System.getProperty("os.name"))
                .totalMemory(totalMemory)
                .freeMemory(freeMemory)
                .usedMemory(usedMemory)
                .memoryUsage(Math.round(memoryUsage * 100.0) / 100.0) // Round to 2 decimal places
                .build();
    }
}
