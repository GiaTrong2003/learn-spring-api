package com.giatrong.learning.learnspringapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data // Annotation của Lombok: Tự động tạo getter, setter, toString()...
@Entity // Đánh dấu class này là một Entity, cần được ánh xạ xuống DB.
@Table(name = "users") // Chỉ định rõ tên của bảng trong DB sẽ là "users".
public class User {

    @Id // Đánh dấu trường này là khóa chính (Primary Key).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID sẽ được tự động tăng bởi MySQL.
    private Long id;

    // nullable = false: Cột này không được phép null.
    // unique = true: Giá trị trong cột này không được trùng lặp.
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Nếu không có @Column, Hibernate sẽ tự tạo cột với tên giống tên trường.
    private String fullName;
}