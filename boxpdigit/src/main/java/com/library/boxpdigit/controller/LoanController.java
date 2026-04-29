package com.library.boxpdigit.controller;

import com.library.boxpdigit.entity.Loan;
import com.library.boxpdigit.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.library.boxpdigit.config.UserPrincipal;
import org.springframework.security.core.Authentication;
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/return/{loanId}")
    public ResponseEntity<?> returnBook(@PathVariable Long loanId) {
        try {
            Loan loan = loanService.returnBook(loanId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long bookId, Authentication authentication) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Long userId = userPrincipal.getId();
            Loan loan = loanService.borrowBook(userId, bookId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}