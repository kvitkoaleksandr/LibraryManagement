package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.dto.UserDto;
import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.mapper.UserMapper;
import com.example.libraryManagement.repository.UserRepository;
import com.example.libraryManagement.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "password123";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String TOKEN = "jwtToken";

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private AuthRequestDto authRequestDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername(USERNAME);
        authRequestDto.setPassword(PASSWORD);

        user = new User();
        user.setUsername(USERNAME);
        user.setPassword(ENCODED_PASSWORD);

        userDto = new UserDto(USERNAME);
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void registerUserSuccessTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(AuthRequestDto.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);
        when(jwtUtil.generateToken(anyString())).thenReturn(TOKEN);

        AuthResponseDto response = userService.registerUser(authRequestDto);

        assertEquals(TOKEN, response.getToken());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Ошибка регистрации: пользователь уже существует")
    void registerUserUserAlreadyExistsTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                userService.registerUser(authRequestDto));

        assertEquals("409 CONFLICT \"Логин уже занят!\"", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Успешная аутентификация пользователя")
    void authenticateUserSuccessTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString())).thenReturn(TOKEN);

        AuthResponseDto response = userService.authenticateUser(authRequestDto);

        assertEquals(TOKEN, response.getToken());
    }

    @Test
    @DisplayName("Ошибка аутентификации: неверный логин или пароль")
    void authenticateUserInvalidCredentialsTest() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        AuthResponseDto response = userService.authenticateUser(authRequestDto);

        assertEquals("Ошибка: Логин или пароль неверны!", response.getToken());
    }

    @Test
    @DisplayName("Получение текущего пользователя по токену: успех")
    void getCurrentUserSuccessTest() {
        when(jwtUtil.extractUsername(anyString())).thenReturn(USERNAME);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto currentUser = userService.getCurrentUser("Bearer " + TOKEN);

        assertEquals(USERNAME, currentUser.getUsername());
    }

    @Test
    @DisplayName("Ошибка получения текущего пользователя: пользователь не найден")
    void getCurrentUserUserNotFoundTest() {
        when(jwtUtil.extractUsername(anyString())).thenReturn(USERNAME);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.getCurrentUser("Bearer " + TOKEN));

        assertEquals("Пользователь не найден", exception.getMessage());
    }
}