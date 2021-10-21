package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.mapper.BookMapper;
import com.mytoy.bookstore.mapper.BookMapperImpl;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final을 가진 필드의 생성자를 자동으로 만들어 준다.
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    /* 책 목록 가져오기 */
    public List<BookDto> list() {
        List<BookDto> bookDtoList = new ArrayList<>();
        List<Book> bookList = bookRepository.findAll();

        for (Book book : bookList) {
            BookMapper bookMapper = new BookMapperImpl();
            BookDto bookDto = bookMapper.toBookDto(book);
            bookDtoList.add(bookDto);
        }

        return bookDtoList;
    }

    /* 책 단건 가져오기 */
    @Transactional
    public BookDto detail(Long bookId){
        Book book = bookRepository.findById(bookId).orElse(null);
        BookMapper bookMapper = new BookMapperImpl();

        BookDto bookDto = bookMapper.toBookDto(book);
        if(bookDto.getThumbnailName() == null) {
            bookDto.setThumbnailName("noImage.jpg");
            bookDto.setThumbnailPath("");
        }

        return bookDto;
    }

    /* 책 등록 하기 */
    @Transactional
    public Long add(BookDto bookDto) throws IOException {
        BookMapper bookMapper = new BookMapperImpl();
        Book book = bookMapper.toBookEntity(bookDto);

        book.saveThumbnail(bookDto.getThumbnail());

        Book saveBook = bookRepository.save(book);
        return saveBook.getId();
    }

    /* 책 수정 하기 */
    @Transactional
    public void edit(Long bookId, BookDto bookDto) throws IOException {
        Book book = bookRepository.findById(bookId).orElse(null);
        book.edit(bookDto);
    }
}
