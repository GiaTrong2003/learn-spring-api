package com.giatrong.learning.learnspringapi.dto.response.Auth;

import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.enums.SwaggerDefaultValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = SwaggerDefaultValue.AUTH_RESPONSE_DESC)
public class AuthResponse {
    
    @Schema(description = SwaggerDefaultValue.JWT_TOKEN_DESC, example = SwaggerDefaultValue.JWT_TOKEN_EXAMPLE)
    private String token;

    @Schema(description = SwaggerDefaultValue.USER_INFO_DESC)
    private User user;
}