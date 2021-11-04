package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.mapper.BookMapper;
import com.mytoy.bookstore.mapper.BookMapperImpl;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final 을 가진 필드의 생성자를 자동으로 만들어 준다.
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private final UserRepository userRepository;

    /* 책 목록 가져오기 */
    public List<BookDto> all() {
        List<BookDto> bookDtoList = new ArrayList<>();
        List<Book> bookList = bookRepository.findAll();

        for (Book book : bookList) {
            BookMapper bookMapper = new BookMapperImpl();
            BookDto bookDto = bookMapper.toBookDto(book);
            if(book.getThumbnailType() == null) bookDto.defaultThumbnail();
            bookDtoList.add(bookDto);
            bookDto.setUid(book.getUser().getUid());
        }

        return bookDtoList;
    }

    /* 책 단건 가져오기 */
    @Transactional
    public BookDto detail(Long bookId){
        Book book = bookRepository.findById(bookId).orElse(null);

        BookMapper bookMapper = new BookMapperImpl();
        BookDto bookDto = bookMapper.toBookDto(book);

        if(book.getThumbnailType() == null) bookDto.defaultThumbnail();
        bookDto.setUid(book.getUser().getUid());

        return bookDto;
    }

    /* 책 등록 하기 */
    @Transactional
    public Long add(BookDto bookDto, String uid) throws IOException {
        BookMapper bookMapper = new BookMapperImpl();
        Book book = bookMapper.toBookEntity(bookDto);

        book.saveThumbnail(bookDto.getThumbnail());

        User user = userRepository.findByUid(uid);
        book.registrant(book, user);
        book.regDate(book);

        Book saveBook = bookRepository.save(book);
        return saveBook.getId();
    }

    /* 책 수정 하기 */
    @Transactional
    public void edit(Long bookId, BookDto bookDto) throws IOException {
        Book book = bookRepository.findById(bookId).orElse(null);
        book.edit(bookDto);
    }

    /* 책 삭제 하기 */
    @Transactional
    public void delete(Long bookId){
        bookRepository.deleteById(bookId);
    }
}
