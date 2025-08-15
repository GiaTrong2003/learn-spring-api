package com.giatrong.learning.learnspringapi.service;

import com.giatrong.learning.learnspringapi.dto.request.User.UserCreateRequest;
import com.giatrong.learning.learnspringapi.dto.request.User.UserUpdateRequest;
import com.giatrong.learning.learnspringapi.dto.response.UserDto;
import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.exception.ResourceNotFoundException;
import com.giatrong.learning.learnspringapi.mapper.UserMapper;
import com.giatrong.learning.learnspringapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // Giả sử bạn có tiêm PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper; // Sử dụng instance này
    private final PasswordEncoder passwordEncoder; // Cần thiết cho việc tạo/cập nhật user

    // Lấy tất cả người dùng
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                // Dùng instance mapper đã được tiêm vào
                .map(userMapper::toDto)
                .toList();
    }

    // Lấy một người dùng theo ID
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                // Dùng instance mapper đã được tiêm vào
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Tạo một người dùng mới từ DTO
    public UserDto createUser(UserCreateRequest request) {
        // Kiểm tra xem username đã tồn tại chưa (ví dụ)
        if (userRepository.getUsersByFullName(request.getFullName())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Dùng mapper để chuyển đổi an toàn từ Request DTO sang Entity
        User user = userMapper.toEntity(request);

        // Xử lý logic nghiệp vụ không thuộc về mapper (như mã hóa mật khẩu)
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    // Cập nhật thông tin người dùng từ DTO
    public UserDto updateUser(Long id, UserUpdateRequest request) {
        // 1. Tìm user hiện tại trong DB
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // 2. Dùng mapper để cập nhật các trường từ DTO vào entity đã có
        // Mapper sẽ tự động bỏ qua các trường null và các trường được đánh dấu @Mapping(ignore=true)
        userMapper.updateEntityFromDto(request, existingUser);

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    // Xóa người dùng
    public void deleteUser(Long id) {
        // Kiểm tra xem user có tồn tại không trước khi xóa
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}