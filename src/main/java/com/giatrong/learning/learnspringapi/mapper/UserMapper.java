package com.giatrong.learning.learnspringapi.mapper;

import com.giatrong.learning.learnspringapi.dto.response.UserDto;
import com.giatrong.learning.learnspringapi.entity.User;
import lombok.Data;

@Data
public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder().id(user.getId()).fullName(user.getFullName()).username(user.getUsername()).build();
    }
}
