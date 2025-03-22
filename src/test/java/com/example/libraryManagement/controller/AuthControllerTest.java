package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private static final String TOKEN = "jwtToken";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "testpassword";

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("Регистрация нового пользователя (успех)")
    void registerUser_Success() {
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setUsername(USERNAME);
        requestDto.setPassword(PASSWORD);

        AuthResponseDto responseDto = new AuthResponseDto(TOKEN);
        when(userService.registerUser(any(AuthRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<AuthResponseDto> response = authController.register(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TOKEN, response.getBody().getToken());
    }

    @Test
    @DisplayName("Аутентификация пользователя (успех)")
    void loginUser_Success() {
        AuthRequestDto requestDto = new AuthRequestDto();
        requestDto.setUsername(USERNAME);
        requestDto.setPassword(PASSWORD);

        AuthResponseDto responseDto = new AuthResponseDto(TOKEN);
        when(userService.authenticateUser(any(AuthRequestDto.class))).thenReturn(responseDto);

        ResponseEntity<AuthResponseDto> response = authController.login(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TOKEN, response.getBody().getToken());
    }
}