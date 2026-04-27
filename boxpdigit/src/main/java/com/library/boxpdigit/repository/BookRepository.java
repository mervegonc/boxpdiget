package com.library.boxpdigit.repository;

import com.library.boxpdigit.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Book entity
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find book by barcode (ISBN)
    Optional<Book> findByBarcode(String barcode);
    
    // Search books by title or author (case insensitive)
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    
    // Check if barcode already exists
    boolean existsByBarcode(String barcode);
    
    // Custom query: find available books (available_copies > 0)
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0")
    List<Book> findAllAvailable();
    
    // Search by title (partial match)
    List<Book> findByTitleContainingIgnoreCase(String title);
}