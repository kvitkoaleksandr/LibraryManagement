package com.example.libraryManagement.mapper;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.UserDto;
import com.example.libraryManagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(AuthRequestDto dto);

    UserDto toDto(User user);
}