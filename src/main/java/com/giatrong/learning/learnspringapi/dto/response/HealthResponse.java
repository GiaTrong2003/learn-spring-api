package com.giatrong.learning.learnspringapi.dto.response;

import com.giatrong.learning.learnspringapi.enums.SwaggerDefaultValue;
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
@Schema(description = SwaggerDefaultValue.HEALTH_RESPONSE_DESC)
public class HealthResponse {

    @Schema(description = SwaggerDefaultValue.OVERALL_STATUS_DESC, example = SwaggerDefaultValue.HEALTH_STATUS_UP)
    private String status;

    @Schema(description = SwaggerDefaultValue.APP_VERSION_DESC, example = SwaggerDefaultValue.APPLICATION_VERSION)
    private String version;

    @Schema(description = SwaggerDefaultValue.TIMESTAMP_DESC, example = SwaggerDefaultValue.HEALTH_TIMESTAMP)
    private LocalDateTime timestamp;

    @Schema(description = SwaggerDefaultValue.UPTIME_DESC, example = SwaggerDefaultValue.APPLICATION_UPTIME)
    private Long uptime;

    @Schema(description = SwaggerDefaultValue.ENVIRONMENT_DESC, example = SwaggerDefaultValue.ENVIRONMENT_PROFILE)
    private String environment;

    @Schema(description = SwaggerDefaultValue.COMPONENT_STATUS_DESC)
    private Map<String, ComponentHealth> components;

    @Schema(description = SwaggerDefaultValue.ADDITIONAL_SYSTEM_INFO_DESC)
    private SystemInfo systemInfo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = SwaggerDefaultValue.COMPONENT_HEALTH_DESC)
    public static class ComponentHealth {
        @Schema(description = SwaggerDefaultValue.COMP_STATUS_DESC, example = SwaggerDefaultValue.HEALTH_STATUS_UP)
        private String status;

        @Schema(description = SwaggerDefaultValue.COMP_DETAILS_DESC)
        private Map<String, Object> details;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = SwaggerDefaultValue.SYSTEM_INFO_DESC)
    public static class SystemInfo {
        @Schema(description = SwaggerDefaultValue.JAVA_VERSION_DESC, example = SwaggerDefaultValue.JAVA_VERSION)
        private String javaVersion;

        @Schema(description = SwaggerDefaultValue.OS_NAME_DESC, example = SwaggerDefaultValue.OS_NAME)
        private String osName;

        @Schema(description = SwaggerDefaultValue.TOTAL_MEMORY_DESC, example = SwaggerDefaultValue.TOTAL_MEMORY)
        private Long totalMemory;

        @Schema(description = SwaggerDefaultValue.FREE_MEMORY_DESC, example = SwaggerDefaultValue.FREE_MEMORY)
        private Long freeMemory;

        @Schema(description = SwaggerDefaultValue.USED_MEMORY_DESC, example = SwaggerDefaultValue.USED_MEMORY)
        private Long usedMemory;

        @Schema(description = SwaggerDefaultValue.MEMORY_USAGE_DESC, example = SwaggerDefaultValue.MEMORY_USAGE_PERCENTAGE)
        private Double memoryUsage;
    }
}
