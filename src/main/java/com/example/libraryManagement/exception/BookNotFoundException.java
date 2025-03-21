package com.example.libraryManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {

    public BookNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Книга с ID " + id + " не найдена.");
    }
}