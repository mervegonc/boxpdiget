package com.library.boxpdigit.controller;

import com.library.boxpdigit.dto.BookInfoDto;
import com.library.boxpdigit.service.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestGoogleBooksController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping("/fetch-book")
    public ResponseEntity<?> fetchBook(@RequestParam String barcode) {
        try {
            BookInfoDto book = googleBooksService.fetchBookByBarcode(barcode);
            if (book == null) {
                return ResponseEntity.status(404).body("Book not found");
            }
            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}