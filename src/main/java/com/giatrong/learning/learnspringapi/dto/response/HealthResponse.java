package com.giatrong.learning.learnspringapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Health check response containing system status and metrics")
public class HealthResponse {

    @Schema(description = "Overall system status", example = "UP")
    private String status;

    @Schema(description = "Application version", example = "1.0.0")
    private String version;

    @Schema(description = "Current server timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Application uptime in milliseconds", example = "3600000")
    private Long uptime;

    @Schema(description = "Environment profile", example = "dev")
    private String environment;

    @Schema(description = "Detailed component health status")
    private Map<String, ComponentHealth> components;

    @Schema(description = "Additional system information")
    private SystemInfo systemInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Individual component health status")
    public static class ComponentHealth {
        @Schema(description = "Component status", example = "UP")
        private String status;

        @Schema(description = "Component details")
        private Map<String, Object> details;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "System information")
    public static class SystemInfo {
        @Schema(description = "Java version", example = "21.0.1")
        private String javaVersion;

        @Schema(description = "Operating system", example = "Mac OS X")
        private String osName;

        @Schema(description = "Total memory in MB", example = "512")
        private Long totalMemory;

        @Schema(description = "Free memory in MB", example = "256")
        private Long freeMemory;

        @Schema(description = "Used memory in MB", example = "256")
        private Long usedMemory;

        @Schema(description = "Memory usage percentage", example = "50.0")
        private Double memoryUsage;
    }
}
