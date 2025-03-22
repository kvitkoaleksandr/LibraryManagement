package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.UserDto;
import com.example.libraryManagement.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String TOKEN = "Bearer valid.jwt.token";
    private static final String USERNAME = "testuser";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    @DisplayName("Получение текущего пользователя по токену: успех")
    void getCurrentUserSuccessTest() {
        UserDto expectedUser = new UserDto(USERNAME);
        when(userService.getCurrentUser(anyString())).thenReturn(expectedUser);

        ResponseEntity<UserDto> response = userController.getCurrentUser(TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USERNAME, response.getBody().getUsername());
    }

    @Test
    @DisplayName("Получение текущего пользователя: пользователь не найден")
    void getCurrentUserNotFoundTest() {
        when(userService.getCurrentUser(anyString())).thenThrow(new RuntimeException("Пользователь не найден"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userController.getCurrentUser(TOKEN));

        assertEquals("Пользователь не найден", exception.getMessage());
    }
}