package com.library.boxpdigit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for creating/updating a book
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private String title;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private String genre;
    private String barcode;
    private String shelfNumber;    // manuel girilecek
    private Integer totalCopies;
    private String thumbnailUrl;
}