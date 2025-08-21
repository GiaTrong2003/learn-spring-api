package com.giatrong.learning.learnspringapi.controller;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/monitoring")
@RequiredArgsConstructor
public class MonitoringController {

    private final MeterRegistry meterRegistry;
    private final HealthEndpoint healthEndpoint;
    private final MetricsEndpoint metricsEndpoint;

    /**
     * Custom dashboard with key metrics
     */
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        // Health status
        dashboard.put("health", healthEndpoint.health());
        
        // Key metrics
        Map<String, Object> metrics = new HashMap<>();
        
        // JVM metrics
        if (meterRegistry.find("jvm.memory.used").gauge() != null) {
            metrics.put("memory_used_mb", 
                meterRegistry.find("jvm.memory.used").gauge().value() / 1024 / 1024);
        }
        
        if (meterRegistry.find("jvm.threads.live").gauge() != null) {
            metrics.put("threads_active", 
                meterRegistry.find("jvm.threads.live").gauge().value());
        }
        
        // HTTP metrics
        if (meterRegistry.find("http.server.requests").timer() != null) {
            Timer httpTimer = meterRegistry.find("http.server.requests").timer();
            metrics.put("http_requests_total", httpTimer.count());
            metrics.put("http_requests_avg_time_ms", httpTimer.mean(TimeUnit.MILLISECONDS) * 1000);
        }
        
        // Custom metrics
        if (meterRegistry.find("user.creation.count").counter() != null) {
            metrics.put("users_created_total", 
                meterRegistry.find("user.creation.count").counter().count());
        }
        
        dashboard.put("metrics", metrics);
        dashboard.put("timestamp", System.currentTimeMillis());
        
        return dashboard;
    }

    /**
     * Get available metrics list
     */
    @GetMapping("/metrics")
    public Map<String, Object> getAvailableMetrics() {
        Map<String, Object> result = new HashMap<>();
        result.put("available_metrics", metricsEndpoint.listNames().getNames());
        return result;
    }
}
