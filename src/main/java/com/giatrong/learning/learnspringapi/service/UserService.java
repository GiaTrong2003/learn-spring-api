package com.giatrong.learning.learnspringapi.service;

import com.giatrong.learning.learnspringapi.dto.response.UserDto;
import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.exception.ResourceNotFoundException;
import com.giatrong.learning.learnspringapi.mapper.UserMapper;
import com.giatrong.learning.learnspringapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Đánh dấu đây là một Bean thuộc tầng Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Phương thức để lấy tất cả người dùng
    public List<UserDto> getAllUsers() {
        List<UserDto> list = userRepository.findAll()
                // call method .stream() to handle each element in the list
                .stream()// convert from List<User> to Stream<User>, Stream is a sequence of elements supporting sequential and parallel aggregate operations
                // using toDto method from UserMapper to convert each User entity to UserDto
                // this sentence will equivalent to: .map(user -> UserMapper.toDto(user))
                .map(UserMapper::toDto)
                // collect the results back to a List<UserDto>
                .toList();
        return list;
    }

    // Phương thức để lấy một người dùng theo ID
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                // Nếu tìm thấy, chuyển đổi sang UserDto, nếu không sẽ ném ra ngoại lệ ResourceNotFoundException;
                // .map() only applies if the Optional contains a value, otherwise ( nếu không thì ) it returns an empty Optional
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Phương thức để tạo một người dùng mới
    public UserDto createUser(User user) {
        // Trong tương lai, chúng ta có thể thêm logic kiểm tra dữ liệu ở đây
        return UserMapper.toDto((userRepository.save(user)));
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