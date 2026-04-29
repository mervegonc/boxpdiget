package com.library.boxpdigit.controller;

import com.library.boxpdigit.dto.BookRequestDto;
import com.library.boxpdigit.dto.BookResponseDto;
import com.library.boxpdigit.service.BookService;
import com.library.boxpdigit.service.GoogleBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Book operations
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GoogleBooksService googleBooksService;

    /**
     * Fetch book details from Google Books API using barcode (ISBN)
     * This does NOT save the book, just returns the info for preview
     */
    @GetMapping("/fetch-from-barcode")
    public ResponseEntity<?> fetchFromBarcode(@RequestParam String barcode) {
        try {
            BookRequestDto bookInfo = googleBooksService.fetchBookRequestDtoByBarcode(barcode);
            if (bookInfo == null) {
                return ResponseEntity.status(404).body("Book not found via Google Books");
            }
            return ResponseEntity.ok(bookInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching book: " + e.getMessage());
        }
    }

    /**
     * Create a new book (Admin only later)
     */
    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDto dto) {
        BookResponseDto saved = bookService.saveBook(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get all books list
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteBook(@PathVariable Long id) {
    try {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}

@PutMapping("/{id}")
public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookRequestDto dto) {
    try {
        BookResponseDto updated = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updated);
    } catch (RuntimeException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
}