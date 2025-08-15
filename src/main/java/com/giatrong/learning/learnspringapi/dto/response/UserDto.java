package com.giatrong.learning.learnspringapi.dto.response;

import com.giatrong.learning.learnspringapi.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Role role;

    // don't return password in the response
}
