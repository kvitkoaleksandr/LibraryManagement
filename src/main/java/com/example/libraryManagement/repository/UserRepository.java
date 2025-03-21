package com.example.libraryManagement.repository;

import com.example.libraryManagement.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    boolean existsByPassword(String encodedPassword);
    void save(User user);
}