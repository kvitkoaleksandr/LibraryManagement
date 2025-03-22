package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.dto.UserDto;
import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.mapper.UserMapper;
import com.example.libraryManagement.repository.UserRepository;
import com.example.libraryManagement.security.JwtUtil;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Transactional
    public AuthResponseDto registerUser(AuthRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Логин {} уже занят.", request.getUsername());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Логин уже занят!");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        try {
            userRepository.save(user);
            log.info("Пользователь успешно зарегистрирован: {}", request.getUsername());
            String token = jwtUtil.generateToken(user.getUsername());
            return new AuthResponseDto(token);
        } catch (PersistenceException e) {
            log.error("Ошибка сохранения пользователя с логином {}: {}", request.getUsername(), e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка при регистрации пользователя.");
        }
    }

    public AuthResponseDto authenticateUser(AuthRequestDto request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getUsername());
                    log.info("Пользователь успешно вошел: {}", request.getUsername());
                    return new AuthResponseDto(token);
                })
                .orElseGet(() -> {
                    log.warn("Неудачная попытка входа: {}", request.getUsername());
                    return new AuthResponseDto("Ошибка: Логин или пароль неверны!");
                });
    }

    public UserDto getCurrentUser(String token) {
        String username = jwtUtil.extractUsername(token);
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден: {}", username);
                    return new RuntimeException("Пользователь не найден");
                });
    }
}