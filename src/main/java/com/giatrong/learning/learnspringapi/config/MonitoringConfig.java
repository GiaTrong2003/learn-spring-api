package com.giatrong.learning.learnspringapi.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MonitoringConfig {

    /**
     * Enable @Timed annotation for method performance monitoring
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}

/**
 * Custom health indicator for API status
 */
@Component
class ApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            // Add your custom health checks here
            // For example: check database connection, external API availability, etc.
            
            boolean isHealthy = checkApiHealth();
            
            if (isHealthy) {
                return Health.up()
                        .withDetail("api", "All systems operational")
                        .withDetail("timestamp", System.currentTimeMillis())
                        .build();
            } else {
                return Health.down()
                        .withDetail("api", "Some issues detected")
                        .withDetail("timestamp", System.currentTimeMillis())
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
    
    private boolean checkApiHealth() {
        // Implement your health check logic
        // For now, return true (healthy)
        return true;
    }
}