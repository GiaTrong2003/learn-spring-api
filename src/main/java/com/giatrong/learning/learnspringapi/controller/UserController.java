package com.giatrong.learning.learnspringapi.controller;

import com.giatrong.learning.learnspringapi.dto.request.User.UserCreateRequest;
import com.giatrong.learning.learnspringapi.dto.request.User.UserUpdateRequest;
import com.giatrong.learning.learnspringapi.dto.response.ApiResponse;
import com.giatrong.learning.learnspringapi.dto.response.UserDto;
import com.giatrong.learning.learnspringapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name="User Management", description = "API for managing users")
@Validated // Enable method-level validation for path/query parameters
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API Lấy tất cả người dùng
    // Endpoint: GET http://localhost:8080/api/v1/users
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        ApiResponse<List<UserDto>> apiResponse = ApiResponse.success(users, "Get all users successfully", HttpStatus.OK.value());
        System.out.println(apiResponse);
        return ResponseEntity.ok(apiResponse);
    }

    // API Lấy người dùng theo ID
    // Endpoint: GET http://localhost:8080/api/v1/users/1
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(
            @PathVariable @Min(value = 1, message = "User ID must be greater than 0") Long id) {
        UserDto user = userService.getUserById(id);
        ApiResponse<UserDto> apiResponse = ApiResponse.success(user, "Get user by ID successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    // API Tạo người dùng mới
    // Endpoint: POST http://localhost:8080/api/v1/users
    @PostMapping
    @Operation(summary = "Create new user", description = "Create a new user with validation")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserCreateRequest user) {
        UserDto createdUser = userService.createUser(user);
        ApiResponse<UserDto> apiResponse = ApiResponse.success(createdUser, "User created successfully", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // API Cập nhật người dùng
    // Endpoint: PUT http://localhost:8080/api/v1/users/1
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // need JWT token to access this endpoint
    @SecurityRequirement(name = "bearerAuth") // Swagger security requirement for JWT token
    @Operation(summary = "Update user", description = "Update user information with validation")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable @Min(value = 1, message = "User ID must be greater than 0") Long id, 
            @Valid @RequestBody UserUpdateRequest userDetails) {
        UserDto updatedUser = userService.updateUser(id, userDetails);
        ApiResponse<UserDto> apiResponse = ApiResponse.success(updatedUser, "User updated successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }

    // API Xóa người dùng
    // Endpoint: DELETE http://localhost:8080/api/v1/users/1
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable @Min(value = 1, message = "User ID must be greater than 0") Long id) {
        userService.deleteUser(id);
        ApiResponse<String> apiResponse = ApiResponse.success("", "User deleted successfully", HttpStatus.OK.value());
        return ResponseEntity.ok(apiResponse);
    }
}