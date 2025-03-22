package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private static final Long BOOK_ID = 1L;
    private static final String BOOK_TITLE = "Test Book";

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    @DisplayName("Получение списка всех книг")
    void getAllBooksSuccessTest() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(BOOK_TITLE);
        when(bookService.getAllBooks()).thenReturn(List.of(bookDto));

        List<BookDto> books = bookController.getAllBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(BOOK_TITLE, books.get(0).getTitle());
    }

    @Test
    @DisplayName("Получение книги по ID: успех")
    void getBookByIdSuccessTest() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(BOOK_TITLE);
        when(bookService.getBookById(anyLong())).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.getBookById(BOOK_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(BOOK_TITLE, response.getBody().getTitle());
    }

    @Test
    @DisplayName("Получение книги по ID: книга не найдена")
    void getBookByIdNotFoundTest() {
        when(bookService.getBookById(anyLong())).thenReturn(null);

        ResponseEntity<BookDto> response = bookController.getBookById(BOOK_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Создание новой книги: успех")
    void createBookSuccessTest() {
        BookDto requestDto = new BookDto();
        requestDto.setTitle(BOOK_TITLE);

        when(bookService.createBook(any(BookDto.class))).thenReturn(requestDto);

        ResponseEntity<BookDto> response = bookController.createBook(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(BOOK_TITLE, response.getBody().getTitle());
    }

    @Test
    @DisplayName("Обновление книги: успех")
    void updateBookSuccessTest() {
        BookDto requestDto = new BookDto();
        requestDto.setTitle(BOOK_TITLE);

        when(bookService.updateBook(anyLong(), any(BookDto.class))).thenReturn(requestDto);

        ResponseEntity<BookDto> response = bookController.updateBook(BOOK_ID, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(BOOK_TITLE, response.getBody().getTitle());
    }

    @Test
    @DisplayName("Обновление книги: книга не найдена")
    void updateBookNotFoundTest() {
        BookDto requestDto = new BookDto();

        when(bookService.updateBook(anyLong(), any(BookDto.class))).thenReturn(null);

        ResponseEntity<BookDto> response = bookController.updateBook(BOOK_ID, requestDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Удаление книги: успех")
    void deleteBookSuccessTest() {
        doNothing().when(bookService).deleteBook(anyLong());

        ResponseEntity<Void> response = bookController.deleteBook(BOOK_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Поиск книг: успех")
    void searchBooksSuccessTest() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(BOOK_TITLE);
        when(bookService.searchBooks(anyString())).thenReturn(List.of(bookDto));

        List<BookDto> response = bookController.searchBooks("Test");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(BOOK_TITLE, response.get(0).getTitle());
    }
}