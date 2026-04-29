package com.library.boxpdigit.service;

import com.library.boxpdigit.entity.Book;
import com.library.boxpdigit.entity.Loan;
import com.library.boxpdigit.entity.User;
import com.library.boxpdigit.repository.BookRepository;
import com.library.boxpdigit.repository.LoanRepository;
import com.library.boxpdigit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private static final int LOAN_DAYS = 14;       // ödünç süresi (gün)
    private static final double FINE_PER_DAY = 1.0; // gecikme cezası (TL/gün)

    /**
     * Borrow a book for a user
     * @param userId user id (borrower)
     * @param bookId book id
     * @return saved Loan entity
     */
    @Transactional
    public Loan borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // check if book has available copies
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies of this book");
        }

        // check if user has unpaid fines > 5 TL (example threshold)
        if (user.getTotalFine() != null && user.getTotalFine() > 5.0) {
            throw new RuntimeException("User has unpaid fines. Please pay before borrowing.");
        }

        // create loan
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(LOAN_DAYS));
        loan.setIsReturned(false);
        loan.setFine(0.0);

        // decrease available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    /**
     * Return a book and calculate fine if overdue
     * @param loanId loan id
     * @return updated Loan with return date and fine
     */
    @Transactional
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (Boolean.TRUE.equals(loan.getIsReturned())) {
            throw new RuntimeException("Book already returned");
        }

        LocalDate returnDate = LocalDate.now();
        loan.setReturnDate(returnDate);
        loan.setIsReturned(true);

        // calculate fine if overdue
        if (returnDate.isAfter(loan.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
            double fine = daysOverdue * FINE_PER_DAY;
            loan.setFine(fine);

            // add fine to user's totalFine
            User user = loan.getUser();
            user.setTotalFine(user.getTotalFine() + fine);
            userRepository.save(user);
        }

        // increase available copies of the book
        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }
}