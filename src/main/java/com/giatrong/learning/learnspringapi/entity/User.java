package com.giatrong.learning.learnspringapi.entity;

import com.giatrong.learning.learnspringapi.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 1 , max = 50, message = "Username must be between 1 and 50 characters")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    private String password;

    private String fullName;

    @Column(unique = true)
    @Email(message="Email should be valid")
    private String email;

    private Role role; // Ví dụ: "USER", "ADMIN", v.v.

    // NOTE: Trong thực tế, bạn sẽ cần thêm một trường role, ví dụ:
    // @Enumerated(EnumType.STRING)
    // private Role role;
    // Và getAuthorities() sẽ trả về role đó. Ở đây ta làm đơn giản.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về danh sách quyền của người dùng.
        // Ví dụ: return List.of(new SimpleGrantedAuthority(role.name()));
        return List.of(); // Để đơn giản, ta trả về danh sách rỗng
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}