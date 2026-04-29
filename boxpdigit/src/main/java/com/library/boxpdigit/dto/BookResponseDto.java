package com.library.boxpdigit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO for returning book data to the frontend
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer publicationYear;
    private String genre;
    private String barcode;
    private String shelfNumber;
    private Integer availableCopies;
    private Integer totalCopies;
    private String thumbnailUrl;
}