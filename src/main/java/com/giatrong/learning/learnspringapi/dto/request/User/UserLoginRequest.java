package com.giatrong.learning.learnspringapi.dto.request.User;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
