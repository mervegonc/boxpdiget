package com.library.boxpdigit.repository;

import com.library.boxpdigit.entity.Loan;
import com.library.boxpdigit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import com.library.boxpdigit.entity.Book;
/**
 * Repository for Loan entity
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    // Find all loans of a specific user (including returned)
    List<Loan> findByUserOrderByLoanDateDesc(User user);
    
    // Find active loans (not returned yet) for a user
    List<Loan> findByUserAndIsReturnedFalse(User user);
    
    // Find all active loans (not returned)
    List<Loan> findByIsReturnedFalse();
    
    // Find all overdue loans (due_date < current date and not returned)
    @Query("SELECT l FROM Loan l WHERE l.dueDate < CURRENT_DATE AND l.isReturned = false")
    List<Loan> findAllOverdueLoans();
    
    // Check if a user currently has a specific book on loan
    boolean existsByUserAndBookAndIsReturnedFalse(User user, Book book);
    
    // Find loan by id and user
    Optional<Loan> findByIdAndUser(Long id, User user);
}