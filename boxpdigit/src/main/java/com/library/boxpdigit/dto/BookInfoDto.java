package com.library.boxpdigit.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoDto {
    private String title;
    private String authors;      // comma-separated if multiple
    private String publisher;
    private Integer publishedYear;
    private String categories;   // first category
    private String thumbnail;
    private String barcode;
}