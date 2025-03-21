package com.example.libraryManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {

    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 4, max = 20, message = "Логин должен быть от 4 до 20 символов")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 50, message = "Пароль должен быть от 6 до 50 символов")
    private String password;
}