package com.giatrong.learning.learnspringapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Configuration Logger - Log tất cả config values trong một dòng duy nhất
 */
@Component
@Slf4j
public class ConfigurationLogger {

    @Value("${spring.datasource.url:N/A}")
    private String datasourceUrl;

    @Value("${spring.datasource.username:N/A}")
    private String datasourceUsername;

    @Value("${spring.datasource.password:N/A}")
    private String datasourcePassword;

    @Value("${spring.data.redis.host:N/A}")
    private String redisHost;

    @Value("${spring.data.redis.port:N/A}")
    private String redisPort;

    @Value("${jwt.access.secret:N/A}")
    private String jwtAccessSecret;

    @Value("${jwt.access.expiration:N/A}")
    private String jwtAccessExpiration;

    @Value("${jwt.refresh.secret:N/A}")
    private String jwtRefreshSecret;

    @Value("${jwt.refresh.expiration:N/A}")
    private String jwtRefreshExpiration;

    @Value("${MYSQL_PORT:3306}")
    private String mysqlPort;

    @Value("${MYSQL_DATABASE:user_management}")
    private String mysqlDatabase;

    @Value("${MYSQL_USER:root}")
    private String mysqlUser;

    @Value("${MYSQL_PASSWORD:123456}")
    private String mysqlPassword;

    private final Environment environment;

    public ConfigurationLogger(Environment environment) {
        this.environment = environment;
    }

    /**
     * Log tất cả configuration trong một dòng duy nhất
     */
    @EventListener(ApplicationReadyEvent.class)
    public void logConfigurationValues() {
        String[] activeProfiles = environment.getActiveProfiles();
        String serverPort = environment.getProperty("server.port", "8080");
        
        log.info("🔧 CONFIG: Profile=[{}] Server=[{}] DB=[{}:{}@{}:{}] Redis=[{}:{}] JWT=[{}s/{}s] MySQL_ENV=[{}:{}@{}:{}]",
                activeProfiles.length > 0 ? String.join(",", activeProfiles) : "default",
                serverPort,
                datasourceUsername,
                maskPassword(datasourcePassword),
                extractHost(datasourceUrl),
                extractPort(datasourceUrl),
                redisHost,
                redisPort,
                jwtAccessExpiration,
                jwtRefreshExpiration,
                mysqlUser,
                maskPassword(mysqlPassword),
                mysqlDatabase,
                mysqlPort
        );
    }

    private String maskPassword(String password) {
        if (password == null || password.isEmpty() || password.equals("N/A")) {
            return "***";
        }
        return "***";
    }

    private String extractHost(String url) {
        if (url == null || url.equals("N/A")) return "N/A";
        try {
            return url.contains("localhost") ? "localhost" : url.split("//")[1].split(":")[0];
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String extractPort(String url) {
        if (url == null || url.equals("N/A")) return "N/A";
        try {
            String[] parts = url.split(":");
            return parts.length > 3 ? parts[3].split("/")[0] : "3306";
        } catch (Exception e) {
            return "3306";
        }
    }
}
