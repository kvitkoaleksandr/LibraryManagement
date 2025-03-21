package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.mapper.BookMapper;
import com.example.libraryManagement.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        return bookRepository.findById(id)
                .map(book -> {
                    Book updatedBook = bookMapper.toEntity(bookDto);
                    updatedBook.setId(book.getId());
                    return bookMapper.toDto(bookRepository.save(updatedBook));
                })
                .orElse(null);
    }

    @Transactional
    public boolean deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
}