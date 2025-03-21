package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.mapper.BookMapper;
import com.example.libraryManagement.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        log.info("Книга добавлена: id={}, название='{}'", savedBook.getId(), savedBook.getTitle());
        return bookMapper.toDto(savedBook);
    }

    public List<BookDto> getAllBooks() {
        log.info("Запрос всех книг");
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    public BookDto getBookById(Long id) {
        log.info("Запрос книги по id={}", id);
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        Book updatedBook = bookMapper.toEntity(bookDto);
        updatedBook.setId(book.getId());
        Book savedBook = bookRepository.save(updatedBook);

        log.info("Книга обновлена: id={}, новое название='{}'", savedBook.getId(), savedBook.getTitle());
        return bookMapper.toDto(savedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        boolean deleted = bookRepository.deleteById(id);
        if (!deleted) {
            log.warn("Попытка удаления несуществующей книги с id={}", id);
            throw new BookNotFoundException(id);
        }
        log.info("Книга удалена: id={}", id);
    }

    public List<BookDto> searchBooks(String query) {
        log.info("Поиск книг по запросу '{}'", query);
        return bookRepository.findByTitleOrAuthorOrGenre(query)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}