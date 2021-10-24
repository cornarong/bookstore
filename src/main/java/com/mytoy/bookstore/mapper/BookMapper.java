package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper{
//    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    // source: 인자로 받은 객체. target: 리턴 대상인 객체.
    // dto와 entity의 속성 이름이 동일하며, 별도의 임의의 매핑이 필요없다면 @Mapping 생략 가능

//    @Mapping(target = "user", ignore = true)
    Book toBookEntity(BookDto bookDto);

    BookDto toBookDto(Book book);

}
