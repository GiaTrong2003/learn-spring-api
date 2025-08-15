package com.giatrong.learning.learnspringapi.dto.request.User;

import lombok.Data;

@Data // Using Lombok to generate getters, setters, toString, equals, and hashCode methods
public class UserCreateRequest {
    private String username;
    private String password;
    private String fullName; // Full name of the user, can be null or empty
    private String email;
}
