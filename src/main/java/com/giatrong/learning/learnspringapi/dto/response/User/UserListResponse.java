package com.giatrong.learning.learnspringapi.dto.response.User;

import com.giatrong.learning.learnspringapi.dto.dtos.User.UserDto;
import com.giatrong.learning.learnspringapi.dto.request.User.UserListRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for a list of users")
public class UserListResponse {
    private Page<UserDto> data;
    private UserListRequest filter;
}
