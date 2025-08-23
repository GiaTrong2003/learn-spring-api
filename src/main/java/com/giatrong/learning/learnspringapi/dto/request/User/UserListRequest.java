package com.giatrong.learning.learnspringapi.dto.request.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserListRequest {
    private Long id;
    private String email;
    private String fullName;
    private String username;
    private String role;
}
