package com.giatrong.learning.learnspringapi.controller;

import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import com.giatrong.learning.learnspringapi.dto.response.HealthResponse;
import com.giatrong.learning.learnspringapi.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Health Check", description = "API for system health monitoring and status checking")
public class HealthController {

    private final HealthService healthService;

    /**
     * Health Check API - Kiểm tra tình trạng hoạt động của hệ thống
     * Endpoint: GET http://localhost:8080/api/v1/health
     * 
     * Endpoint này cung cấp thông tin về:
     * - Trạng thái tổng thể của hệ thống (UP/DOWN)
     * - Kết nối cơ sở dữ liệu
     * - Thông tin memory và system
     * - Uptime của ứng dụng
     * - Version và environment
     */
    @GetMapping("/health")
    @Operation(
        summary = "Check system health status",
        description = "Comprehensive health check endpoint that verifies database connectivity, " +
                     "system resources, application status, and returns detailed health metrics. " +
                     "This endpoint is publicly accessible and does not require authentication."
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Health check completed successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "503", 
            description = "Service unavailable - health check failed"
        )
    })
    public ResponseEntity<ApiResponse<HealthResponse>> checkHealth() {
        log.info("Health check endpoint called");
        
        try {
            HealthResponse healthResponse = healthService.getHealthStatus();
            
            // Determine HTTP status based on overall health
            HttpStatus httpStatus = "UP".equals(healthResponse.getStatus()) 
                ? HttpStatus.OK 
                : HttpStatus.SERVICE_UNAVAILABLE;
            
            String message = "UP".equals(healthResponse.getStatus())
                ? "System is healthy and operational"
                : "System health check detected issues";
            
            ApiResponse<HealthResponse> apiResponse = ApiResponse.success(
                healthResponse,
                message,
                httpStatus.value()
            );
            
            log.info("Health check completed with status: {}", healthResponse.getStatus());
            return ResponseEntity.status(httpStatus).body(apiResponse);
            
        } catch (Exception e) {
            log.error("Health check endpoint failed", e);
            
            HealthResponse errorResponse = HealthResponse.builder()
                .status("DOWN")
                .timestamp(java.time.LocalDateTime.now())
                .build();
            
            ApiResponse<HealthResponse> apiResponse = ApiResponse.success(
                errorResponse,
                "Health check failed: " + e.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value()
            );
            
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(apiResponse);
        }
    }

    /**
     * Simple Health Check API - Endpoint đơn giản để kiểm tra nhanh
     * Endpoint: GET http://localhost:8080/api/v1/health/simple
     */
    @GetMapping("/health/simple")
    @Operation(
        summary = "Simple health check",
        description = "Lightweight health check endpoint that returns basic application status. " +
                     "Faster than the full health check as it doesn't perform detailed system checks."
    )
    public ResponseEntity<ApiResponse<String>> simpleHealthCheck() {
        log.debug("Simple health check endpoint called");
        
        return ResponseEntity.ok(
            ApiResponse.success(
                "Application is running",
                "Simple health check successful",
                HttpStatus.OK.value()
            )
        );
    }
}
