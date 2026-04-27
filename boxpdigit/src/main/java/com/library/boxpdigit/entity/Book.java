package com.library.boxpdigit.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;

    private String publisher;

    @Column(name = "publication_year")
    private Integer publicationYear;

    private String genre;

    @Column(unique = true)
    private String barcode; // ISBN

    @Column(name = "shelf_number", nullable = false)
    private String shelfNumber;

    @Column(name = "total_copies")
    private Integer totalCopies = 1;

    @Column(name = "available_copies")
    private Integer availableCopies = 1;

    @Column(name = "thumbnail_url", length = 1000)
    private String thumbnailUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
