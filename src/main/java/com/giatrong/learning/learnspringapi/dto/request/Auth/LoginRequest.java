package com.giatrong.learning.learnspringapi.dto.request.Auth;

import com.giatrong.learning.learnspringapi.enums.SwaggerDefaultValue;
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
@Schema(description = SwaggerDefaultValue.USER_LOGIN_DESC)
public class LoginRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(description = SwaggerDefaultValue.USERNAME_AUTH_DESC, example = SwaggerDefaultValue.USERNAME_EXAMPLE, required = true)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 1, max = 100, message = "Password cannot be empty")
    @Schema(description = SwaggerDefaultValue.PASSWORD_AUTH_DESC, example = SwaggerDefaultValue.PASSWORD_EXAMPLE, required = true)
    private String password;
}
