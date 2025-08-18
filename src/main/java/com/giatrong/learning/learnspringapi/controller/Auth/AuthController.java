package com.giatrong.learning.learnspringapi.controller.Auth;

import com.giatrong.learning.learnspringapi.dto.request.Auth.LoginRequest;
import com.giatrong.learning.learnspringapi.dto.request.Auth.RegisterRequest;
import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import com.giatrong.learning.learnspringapi.dto.response.Auth.AuthResponse;
//import io.swagger.v3.oas.annotations.parameters.RequestBody; -> sai :)))
import com.giatrong.learning.learnspringapi.service.auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @RequestBody RegisterRequest request // use @RequestBody to bind the request body to RegisterRequest object
    ) {
        AuthResponse authResponse = authService.register(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.success(
                authResponse,
                "User registered successfully",
                HttpStatus.CREATED.value()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody LoginRequest request
    ) {
        AuthResponse authResponse = authService.login(request);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.success(
                authResponse,
                "User logged in successfully",
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(apiResponse);
    };

}