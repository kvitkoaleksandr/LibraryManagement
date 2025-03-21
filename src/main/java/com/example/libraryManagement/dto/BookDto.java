package com.example.libraryManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDto {

    @NotBlank(message = "Название книги не может быть пустым")
    @Size(min = 2, max = 100, message = "Название книги должно быть от 2 до 100 символов")
    private String title;

    @NotBlank(message = "Автор не может быть пустым")
    @Size(min = 2, max = 50, message = "Автор должен быть от 2 до 50 символов")
    private String author;

    @NotBlank(message = "Жанр не может быть пустым")
    private String genre;

    private String description;

    @NotBlank(message = "Дата публикации не может быть пустой")
    private String publishDate;
}