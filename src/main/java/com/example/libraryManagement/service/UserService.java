package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.AuthResponseDto;
import com.example.libraryManagement.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    AuthResponseDto registerUser(AuthRequestDto request);
    AuthResponseDto authenticateUser(AuthRequestDto request);
    User getCurrentUser(String username);
}
