package com.giatrong.learning.learnspringapi.dto.response.Auth;

import com.giatrong.learning.learnspringapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;

    private User user;
}