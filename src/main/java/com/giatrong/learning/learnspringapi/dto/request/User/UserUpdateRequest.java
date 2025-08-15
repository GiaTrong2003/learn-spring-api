package com.giatrong.learning.learnspringapi.dto.request.User;

import com.giatrong.learning.learnspringapi.enums.Role;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private Role role;
}
