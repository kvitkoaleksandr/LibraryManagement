package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Регистрация нового пользователя (автоматическая авторизация)")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody AuthRequestDto request) {
        AuthResponseDto response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Аутентификация пользователя и выдача JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto request) {
        AuthResponseDto response = userService.authenticateUser(request);
        return ResponseEntity.ok(response);
    }
}