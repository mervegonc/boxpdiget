package com.library.boxpdigit.service;

import com.library.boxpdigit.dto.BookRequestDto;
import com.library.boxpdigit.dto.BookResponseDto;
import com.library.boxpdigit.entity.Book;
import com.library.boxpdigit.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Book CRUD operations
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookResponseDto saveBook(BookRequestDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setPublicationYear(dto.getPublicationYear());
        book.setGenre(dto.getGenre());
        book.setBarcode(dto.getBarcode());
        book.setShelfNumber(dto.getShelfNumber());
        book.setTotalCopies(dto.getTotalCopies() != null ? dto.getTotalCopies() : 1);
        book.setAvailableCopies(book.getTotalCopies()); // initially same as total
        book.setThumbnailUrl(dto.getThumbnailUrl());

        Book saved = bookRepository.save(book);
        return mapToResponseDto(saved);
    }

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private BookResponseDto mapToResponseDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setGenre(book.getGenre());
        dto.setBarcode(book.getBarcode());
        dto.setShelfNumber(book.getShelfNumber());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setThumbnailUrl(book.getThumbnailUrl());
        return dto;
    }
}