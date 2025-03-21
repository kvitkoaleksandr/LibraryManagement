package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.dto.UserDto;
import com.example.libraryManagement.entity.User;
import com.example.libraryManagement.mapper.UserMapper;
import com.example.libraryManagement.repository.UserRepository;
import com.example.libraryManagement.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
            return new AuthResponseDto("Ошибка: Логин уже занят!");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponseDto(token);
    }

    public AuthResponseDto authenticateUser(AuthRequestDto request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> new AuthResponseDto(jwtUtil.generateToken(user.getUsername())))
                .orElse(new AuthResponseDto("Ошибка: Логин или пароль неверны!"));
    }

    public UserDto getCurrentUser(String token) {
        String username = jwtUtil.extractUsername(token);
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}