package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper{
    // source: 인자로 받은 객체. target: 리턴 대상인 객체.

    Book toBookEntity(BookDto bookDto);

    BookDto toBookDto(Book book);

}
