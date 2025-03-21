package com.example.libraryManagement.controller;


import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.entity.Book;

import com.example.libraryManagement.service.impl.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // Эндпоинт для получения списка всех книг
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Эндпоинт для получения книги по id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Эндпоинт для создания новой книги
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDto bookDto) {
        Book book = bookService.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // Эндпоинт для обновления книги
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        Book updatedBook = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }

    // Эндпоинт для удаления книги
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}