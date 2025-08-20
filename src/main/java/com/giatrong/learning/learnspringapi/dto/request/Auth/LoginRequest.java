package com.giatrong.learning.learnspringapi.dto.request.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User login request")
public class LoginRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = "Username for authentication", example = "john_doe123", required = true)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 1, max = 100, message = "Password cannot be empty")
    @Schema(description = "Password for authentication", example = "SecurePass123!", required = true)
    private String password;
}
