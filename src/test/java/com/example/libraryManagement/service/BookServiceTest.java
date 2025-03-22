package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.entity.Book;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.mapper.BookMapper;
import com.example.libraryManagement.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    private static final Long BOOK_ID = 1L;
    private static final String BOOK_TITLE = "Test Book";

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(BOOK_ID);
        book.setTitle(BOOK_TITLE);

        bookDto = new BookDto();
        bookDto.setTitle(BOOK_TITLE);
    }

    @Test
    @DisplayName("Успешное создание книги")
    void createBook_Success() {
        when(bookMapper.toEntity(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.createBook(bookDto);

        assertEquals(BOOK_TITLE, result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Получение списка всех книг")
    void getAllBooks_Success() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        List<BookDto> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(BOOK_TITLE, result.get(0).getTitle());
    }

    @Test
    @DisplayName("Успешное получение книги по ID")
    void getBookById_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.getBookById(BOOK_ID);

        assertEquals(BOOK_TITLE, result.getTitle());
    }

    @Test
    @DisplayName("Ошибка при получении книги по ID (книга не найдена)")
    void getBookById_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(BOOK_ID));
    }

    @Test
    @DisplayName("Успешное обновление книги")
    void updateBook_Success() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toEntity(any(BookDto.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        BookDto result = bookService.updateBook(BOOK_ID, bookDto);

        assertEquals(BOOK_TITLE, result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    @DisplayName("Ошибка при обновлении книги (книга не найдена)")
    void updateBook_NotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(BOOK_ID, bookDto));
    }

    @Test
    @DisplayName("Успешное удаление книги")
    void deleteBook_Success() {
        when(bookRepository.deleteById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteBook(BOOK_ID));
        verify(bookRepository).deleteById(BOOK_ID);
    }

    @Test
    @DisplayName("Ошибка при удалении книги (книга не найдена)")
    void deleteBook_NotFound() {
        when(bookRepository.deleteById(anyLong())).thenReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(BOOK_ID));
    }

    @Test
    @DisplayName("Поиск книг по запросу")
    void searchBooks_Success() {
        when(bookRepository.findByTitleOrAuthorOrGenre(anyString())).thenReturn(List.of(book));
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDto);

        List<BookDto> result = bookService.searchBooks("Test");

        assertEquals(1, result.size());
        assertEquals(BOOK_TITLE, result.get(0).getTitle());
    }
}