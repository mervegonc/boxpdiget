package com.library.boxpdigit.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // will be encoded with BCrypt

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "student_number", unique = true)
    private String studentNumber; // optional

    @Column(nullable = false)
    private String role; // "ADMIN" or "MEMBER"

    @Column(name = "total_fine")
    private Double totalFine = 0.0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}