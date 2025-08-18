package com.giatrong.learning.learnspringapi.service.auth;

import com.giatrong.learning.learnspringapi.dto.request.Auth.LoginRequest;
import com.giatrong.learning.learnspringapi.dto.request.Auth.RegisterRequest;
import com.giatrong.learning.learnspringapi.dto.response.Auth.AuthResponse;
import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.repository.UserRepository;
import com.giatrong.learning.learnspringapi.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // hash passwords
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackOn =  Exception.class)
    public AuthResponse register(RegisterRequest request) {
        // 1. create a new User object from the RegisterRequest
        var user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                // 2. hash the password before saving it
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        // 3. save the User to the database
        var savedUser = userRepository.save(user);

        // 4. generate a JWT token for the saved user
        var jwtToken = jwtService.generateToken(savedUser);

        return AuthResponse.builder().token(jwtToken).user(savedUser).build();
    }

    @Transactional(rollbackOn =  Exception.class)
    public AuthResponse login(LoginRequest request) {
        // 1. use Spring Security to authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. if authentication is successful, find the user by username
        var user = userRepository.findUserByUsername(request.getUsername());

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder().token(jwtToken).user(user).build();
    }
}