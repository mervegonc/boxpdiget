package com.library.boxpdigit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookDto {
    private String title;
    private List<String> authors;
    private String publisher;
    @JsonProperty("publishedDate")
    private String publishedDate;
    private List<String> categories;
    @JsonProperty("imageLinks")
    private ImageLinks imageLinks;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinks {
        private String thumbnail;
    }
}