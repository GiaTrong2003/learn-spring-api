package com.giatrong.learning.learnspringapi.dto.request.User;

import com.giatrong.learning.learnspringapi.enums.SwaggerDefaultValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = SwaggerDefaultValue.USER_CREATE_DESC)
public class UserCreateRequest {
    
    @NotBlank(message = SwaggerDefaultValue.USERNAME_REQUIRED)
    @Size(min = 3, max = 50, message = SwaggerDefaultValue.USERNAME_SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = SwaggerDefaultValue.USERNAME_PATTERN)
    @Schema(description = SwaggerDefaultValue.USERNAME_UNIQUE_DESC, example = SwaggerDefaultValue.USERNAME_EXAMPLE, required = true)
    private String username;
    
    @NotBlank(message = SwaggerDefaultValue.PASSWORD_REQUIRED)
    @Size(min = 8, max = 100, message = SwaggerDefaultValue.PASSWORD_SIZE)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].*$", 
             message = SwaggerDefaultValue.PASSWORD_PATTERN)
    @Schema(description = SwaggerDefaultValue.PASSWORD_ACCOUNT_DESC, example = SwaggerDefaultValue.PASSWORD_EXAMPLE, required = true)
    private String password;
    
    @NotBlank(message = SwaggerDefaultValue.FULL_NAME_REQUIRED)
    @Size(min = 2, max = 100, message = SwaggerDefaultValue.FULL_NAME_SIZE)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = SwaggerDefaultValue.FULL_NAME_PATTERN)
    @Schema(description = SwaggerDefaultValue.FULL_NAME_DESC, example = SwaggerDefaultValue.FULL_NAME_EXAMPLE, required = true)
    private String fullName;
    
    @NotBlank(message = SwaggerDefaultValue.EMAIL_REQUIRED)
    @Email(message = SwaggerDefaultValue.EMAIL_VALID)
    @Size(max = 100, message = SwaggerDefaultValue.EMAIL_SIZE)
    @Schema(description = SwaggerDefaultValue.EMAIL_DESC, example = SwaggerDefaultValue.EMAIL_EXAMPLE, required = true)
    private String email;
}
