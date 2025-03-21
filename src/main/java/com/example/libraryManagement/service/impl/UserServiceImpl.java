package com.example.libraryManagement.service.impl;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.repository.UserRepository;
import com.example.libraryManagement.security.JwtUtil;
import com.example.libraryManagement.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j // <-- логирование
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Optional<User> findByUsername(String username) {
        log.info("Поиск пользователя по логину: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public AuthResponseDto registerUser(AuthRequestDto request) {
        log.info("Регистрация пользователя: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Ошибка регистрации: логин {} уже занят!", request.getUsername());
            return new AuthResponseDto("Ошибка: Логин уже занят!");
        }

        if (userRepository.existsByPassword(passwordEncoder.encode(request.getPassword()))) {
            log.warn("Ошибка регистрации: пароль уже используется!");
            return new AuthResponseDto("Ошибка: Пароль уже используется! Придумайте новый.");
        }

        User user = new User(null, request.getUsername(), passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(request.getUsername());
        log.info("Регистрация успешна: {}", request.getUsername());
        return new AuthResponseDto(token);
    }

    @Override
    public AuthResponseDto authenticateUser(AuthRequestDto request) {
        log.info("Аутентификация пользователя: {}", request.getUsername());

        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 🔹 Проверяем, что введённый пароль совпадает
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

                // 🔹 Проверяем, что у пользователя уже есть токен (он активен)
                if (jwtUtil.isTokenValid(user.getUsername())) {
                    log.warn("Пользователь {} уже аутентифицирован! Повторный вход не требуется.", request.getUsername());
                    return new AuthResponseDto("Ошибка: Вы уже аутентифицированы!");
                }

                // 🔹 Генерируем новый токен, если старого нет
                String token = jwtUtil.generateToken(user.getUsername());
                log.info("Аутентификация успешна: {}", request.getUsername());
                return new AuthResponseDto(token);
            }
        }

        log.warn("Ошибка входа: неверные учетные данные для {}", request.getUsername());
        return new AuthResponseDto("Ошибка: Логин или пароль неверны!");
    }


    @Override
    public User getCurrentUser(String username) {
        log.info("Запрос информации о текущем пользователе: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}