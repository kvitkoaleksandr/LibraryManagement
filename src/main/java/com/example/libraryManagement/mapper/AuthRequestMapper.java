package com.example.libraryManagement.mapper;

import com.example.libraryManagement.dto.AuthRequestDto;
import com.example.libraryManagement.dto.UserDto;
import com.example.libraryManagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthRequestMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(AuthRequestDto authRequestDto);

    UserDto toDto(User user);
}