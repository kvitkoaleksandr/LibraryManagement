package com.example.libraryManagement.service.impl;

import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // Метод для создания новой книги
    @Transactional
    public Book createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setDescription(bookDto.getDescription());
        book.setPublishDate(bookDto.getPublishDate());

        return bookRepository.save(book);
    }

    // Метод для получения книги по id
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Метод для получения всех книг
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Метод для обновления книги
    @Transactional
    public Book updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Книга не найдена"));
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setGenre(bookDto.getGenre());
        book.setDescription(bookDto.getDescription());
        book.setPublishDate(bookDto.getPublishDate());

        return bookRepository.save(book);
    }

    // Метод для удаления книги
    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}