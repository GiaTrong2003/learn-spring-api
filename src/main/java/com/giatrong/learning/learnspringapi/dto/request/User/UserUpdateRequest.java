package com.giatrong.learning.learnspringapi.dto.request.User;

import com.giatrong.learning.learnspringapi.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "User update request")
public class UserUpdateRequest {
    
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Full name can only contain letters and spaces")
    @Schema(description = "Full name of the user", example = "John Doe Smith")
    private String fullName;
    
    @Email(message = "Email must be a valid email address")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Schema(description = "Email address of the user", example = "john.smith@example.com")
    private String email;
    
    @Schema(description = "Role of the user", example = "USER")
    private Role role;
}
