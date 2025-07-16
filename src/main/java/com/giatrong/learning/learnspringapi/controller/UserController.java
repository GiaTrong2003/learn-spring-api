package com.giatrong.learning.learnspringapi.controller;

import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Đánh dấu đây là một REST Controller, chuyên tạo ra các API trả về JSON.
@RequestMapping("/api/v1/users") // Tất cả API trong class này sẽ có chung tiền tố là /api/v1/users
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // API Lấy tất cả người dùng
    // Endpoint: GET http://localhost:8080/api/v1/users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // API Lấy người dùng theo ID
    // Endpoint: GET http://localhost:8080/api/v1/users/1
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user)) // Nếu tìm thấy, trả về status 200 OK và body là user
                .orElse(ResponseEntity.notFound().build()); // Nếu không, trả về status 404 Not Found
    }

    // API Tạo người dùng mới
    // Endpoint: POST http://localhost:8080/api/v1/users
    @PostMapping
    public User createUser(@RequestBody User user) {
        // @RequestBody sẽ tự động chuyển JSON từ client gửi lên thành đối tượng User
        return userService.createUser(user);
    }

    // API Cập nhật người dùng
    // Endpoint: PUT http://localhost:8080/api/v1/users/1
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // API Xóa người dùng
    // Endpoint: DELETE http://localhost:8080/api/v1/users/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build(); // Trả về status 200 và không có body
    }
}