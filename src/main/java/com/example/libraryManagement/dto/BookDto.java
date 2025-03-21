package com.example.libraryManagement.dto;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String author;
    private String genre;
    private String description;
    private String publishDate;
}