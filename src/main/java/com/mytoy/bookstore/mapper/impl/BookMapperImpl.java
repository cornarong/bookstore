package com.mytoy.bookstore.mapper.impl;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.dto.BookDto.BookDtoBuilder;
import com.mytoy.bookstore.mapper.BookMapper;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.Book.BookBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

// ## Book Mapper

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2021-11-15T18:25:53+0900",
        comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public Book toBookEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookBuilder book = Book.builder();

        book.id( bookDto.getId() );
        book.type( bookDto.getType() );
        book.title( bookDto.getTitle() );
        book.subTitle( bookDto.getSubTitle() );
        book.content( bookDto.getContent() );
        book.author( bookDto.getAuthor() );
        book.publisher( bookDto.getPublisher() );
        if ( bookDto.getPublishedDate() != null ) {
            book.publishedDate( LocalDate.parse( bookDto.getPublishedDate() ) );
        }
        if ( bookDto.getRegDate() != null ) {
            book.regDate( LocalDate.parse( bookDto.getRegDate() ) );
        }
        book.price( bookDto.getPrice() );
        book.disRate( bookDto.getDisRate() );
        book.disPrice( bookDto.getDisPrice() );
        book.quantity( bookDto.getQuantity() );
        book.shippingFee( bookDto.getShippingFee() );
        book.thumbnailType( bookDto.getThumbnailType() );
        book.thumbnailPath( bookDto.getThumbnailPath() );

        return book.build();
    }

    @Override
    public BookDto toBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDtoBuilder bookDto = BookDto.builder();

        bookDto.id( book.getId() );
        bookDto.type( book.getType() );
        bookDto.title( book.getTitle() );
        bookDto.subTitle( book.getSubTitle() );
        bookDto.author( book.getAuthor() );
        bookDto.publisher( book.getPublisher() );
        bookDto.price( book.getPrice() );
        bookDto.quantity( book.getQuantity() );
        bookDto.content( book.getContent() );
        if ( book.getPublishedDate() != null ) {
            bookDto.publishedDate( DateTimeFormatter.ISO_LOCAL_DATE.format( book.getPublishedDate() ) );
        }
        if ( book.getRegDate() != null ) {
            bookDto.regDate( DateTimeFormatter.ISO_LOCAL_DATE.format( book.getRegDate() ) );
        }
        bookDto.disRate( book.getDisRate() );
        bookDto.disPrice( book.getDisPrice() );
        bookDto.shippingFee( book.getShippingFee() );
        bookDto.thumbnailType( book.getThumbnailType() );
        bookDto.thumbnailPath( book.getThumbnailPath() );

        return bookDto.build();
    }
}
