package com.example.libraryManagement.mapper;

import com.example.libraryManagement.dto.BookDto;
import com.example.libraryManagement.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDto bookDto);
    BookDto toDto(Book book);
}