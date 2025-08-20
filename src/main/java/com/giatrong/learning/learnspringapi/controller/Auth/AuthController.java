package com.giatrong.learning.learnspringapi.controller.Auth;

import com.giatrong.learning.learnspringapi.dto.request.Auth.LoginRequest;
import com.giatrong.learning.learnspringapi.dto.request.Auth.RegisterRequest;
import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import com.giatrong.learning.learnspringapi.dto.response.Auth.AuthResponse;
import com.giatrong.learning.learnspringapi.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication with comprehensive validation")
public class AuthController {

    private final AuthService authService;

    /**
     * API Đăng ký người dùng mới với validation
     * Endpoint: POST http://localhost:8080/api/v1/auth/register
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", 
               description = "Register a new user with comprehensive validation including username uniqueness, password strength, and proper formatting")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.success(
            authResponse, 
            "User registered successfully", 
            HttpStatus.CREATED.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * API Đăng nhập với validation
     * Endpoint: POST http://localhost:8080/api/v1/auth/login
     */
    @PostMapping("/login")
    @Operation(summary = "User login", 
               description = "Authenticate user with validated credentials and return JWT token")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.success(
            authResponse, 
            "Login successful", 
            HttpStatus.OK.value()
        );
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * API Test endpoint để kiểm tra authentication system
     * Endpoint: GET http://localhost:8080/api/v1/auth/test
     */
    @GetMapping("/test")
    @Operation(summary = "Test authentication system", 
               description = "Public endpoint to verify the authentication system is working properly")
    public ResponseEntity<ApiResponse<String>> test() {
        return ResponseEntity.ok(
            ApiResponse.success(
                "Authentication system is working!", 
                "Test successful", 
                HttpStatus.OK.value()
            )
        );
    }
}