package com.library.boxpdigit.repository;

import com.library.boxpdigit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Find user by email (used for login)
    Optional<User> findByEmail(String email);
    
    // Check if email already exists
    boolean existsByEmail(String email);
    
    // Find user by student number (optional)
    Optional<User> findByStudentNumber(String studentNumber);
    
}