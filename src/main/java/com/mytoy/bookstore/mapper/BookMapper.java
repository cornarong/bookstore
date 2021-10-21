package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    Book toBookEntity(BookDto bookDto);

    BookDto toBookDto(Book book);

}
