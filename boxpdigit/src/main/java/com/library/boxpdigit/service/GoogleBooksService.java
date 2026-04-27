package com.library.boxpdigit.service;

import com.library.boxpdigit.dto.BookInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${google.books.api.key}")  // bu değeri application.properties'e ekleyeceğiz
    private String apiKey;

    /**
     * @param barcode ISBN-10 or ISBN-13
     * @return BookInfoDto with book data, or null if not found
     */
    public BookInfoDto fetchBookByBarcode(String barcode) throws Exception {
        String url = String.format(
            "https://www.googleapis.com/books/v1/volumes?q=isbn:%s&key=%s",
            barcode, apiKey
        );

        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return parseGoogleBooksResponse(jsonResponse, barcode);
        } catch (HttpClientErrorException.NotFound e) {
            // Book not found
            return null;
        } catch (Exception e) {
            throw new Exception("Failed to fetch from Google Books: " + e.getMessage());
        }
    }

    /**
     * Parse JSON response from Google Books API
     */
    private BookInfoDto parseGoogleBooksResponse(String json, String barcode) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        JsonNode items = root.get("items");

        if (items == null || !items.isArray() || items.size() == 0) {
            return null; // no results
        }

        JsonNode volumeInfo = items.get(0).get("volumeInfo");

        String title = volumeInfo.has("title") ? volumeInfo.get("title").asText() : null;
        
        String authors = null;
        if (volumeInfo.has("authors")) {
            authors = StreamSupport.stream(volumeInfo.get("authors").spliterator(), false)
                    .map(JsonNode::asText)
                    .collect(Collectors.joining(", "));
        }

        String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").asText() : null;
        
        Integer publishedYear = null;
        if (volumeInfo.has("publishedDate")) {
            String dateStr = volumeInfo.get("publishedDate").asText();
            if (dateStr.length() >= 4) {
                try {
                    publishedYear = Integer.parseInt(dateStr.substring(0, 4));
                } catch (NumberFormatException e) { /* ignore */ }
            }
        }

        String categories = null;
        if (volumeInfo.has("categories")) {
            categories = volumeInfo.get("categories").get(0).asText();
        }

        String thumbnail = null;
        if (volumeInfo.has("imageLinks") && volumeInfo.get("imageLinks").has("thumbnail")) {
            thumbnail = volumeInfo.get("imageLinks").get("thumbnail").asText();
        }

        return new BookInfoDto(title, authors, publisher, publishedYear, categories, thumbnail, barcode);
    }
}