package com.giatrong.learning.learnspringapi.service;

import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Đánh dấu đây là một Bean thuộc tầng Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Phương thức để lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Phương thức để lấy một người dùng theo ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Phương thức để tạo một người dùng mới
    public User createUser(User user) {
        // Trong tương lai, chúng ta có thể thêm logic kiểm tra dữ liệu ở đây
        return userRepository.save(user);
    }

    // Phương thức để cập nhật thông tin người dùng
    public User updateUser(Long id, User userDetails) {
        // Tìm user trong DB, nếu không có sẽ báo lỗi
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // Cập nhật thông tin từ userDetails vào user đã tìm thấy
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setFullName(userDetails.getFullName());

        // Lưu lại user đã được cập nhật vào DB
        return userRepository.save(user);
    }

    // Phương thức để xóa người dùng
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}