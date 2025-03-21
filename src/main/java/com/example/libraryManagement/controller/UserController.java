package com.example.libraryManagement.controller;

import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.security.JwtUtil;
import com.example.libraryManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "Получить текущего пользователя")
    @GetMapping("/me")
    public User getCurrentUser(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Убираем "Bearer "
        String username = jwtUtil.extractUsername(jwt);
        return userService.getCurrentUser(username);
    }

    @Operation(summary = "Тестовый эндпоинт: создать пользователя (пока без БД)")
    @PostMapping("/test")
    public User createUserTest(@RequestParam String username, @RequestParam String password) {
        return new User(null, username, password);
    }
}