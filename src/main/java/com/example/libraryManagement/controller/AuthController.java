package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j // <-- добавляем логирование
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Регистрация нового пользователя (автоматическая авторизация)")
    @PostMapping("register")
    public AuthResponseDto register(@Valid @RequestBody AuthRequestDto request) {
        log.info("Попытка регистрации: {}", request.getUsername());
        AuthResponseDto response = userService.registerUser(request);
        log.info("Ответ на регистрацию: {}", response.getToken());
        return response;
    }

    @Operation(summary = "Аутентификация пользователя и выдача JWT")
    @PostMapping("login")
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto request) {
        log.info("Попытка входа: {}", request.getUsername());
        AuthResponseDto response = userService.authenticateUser(request);
        log.info("Ответ на вход: {}", response.getToken());
        return response;
    }

    @Operation(summary = "Выход из системы (JWT удаляется на клиенте)")
    @PostMapping("logout")
    public ResponseEntity<String> logout() {
        log.info("Пользователь вышел из системы");
        return ResponseEntity.ok("Вы успешно вышли из системы! Очистите токен на клиенте.");
    }
}