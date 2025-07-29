package com.giatrong.learning.learnspringapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String fullName;

    // don't return password in the response
}
