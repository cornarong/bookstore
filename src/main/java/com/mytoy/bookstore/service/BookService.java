package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.mapper.BookMapper;
import com.mytoy.bookstore.mapper.impl.BookMapperImpl;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.BookType;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    /* 모든,국내,국외 책 목록 가져오기 */
    public Page<BookDto> all(String searchTerm, BookType type, Pageable pageable) {
        Page<Book> bookList;
        if(type == null) {
            bookList = bookRepository.findByTitleContainingOrAuthorContainingOrPublisherContaining(
                    searchTerm, searchTerm, searchTerm, pageable);
        } else {
            bookList = bookRepository.findByTypeAndSearchTerm(type, searchTerm, searchTerm, searchTerm, pageable);
        }
        Page<BookDto> bookDtoList = new BookDto().toDtoList(bookList); // Page<Entity> -> Page<Dto> 변환.
        return bookDtoList;
    }

    /* 모든 책 목록 (발행일 or 등록일 내림차 순) 가져오기*/
    public Page<BookDto> allDesc(String searchTerm, Pageable pageable, String sortType) {
        Page<Book> bookList;
        if(sortType.equals("regDate")){
            bookList = bookRepository.findByTitleContainingOrAuthorContainingOrPublisherContainingOrderByRegDateDesc(
                    searchTerm, searchTerm, searchTerm, pageable);
        }else{
            bookList = bookRepository.findByTitleContainingOrAuthorContainingOrPublisherContainingOrderByPublishedDateDesc(
                    searchTerm, searchTerm, searchTerm, pageable);
        }
        Page<BookDto> bookDtoList = new BookDto().toDtoList(bookList); // Page<Entity> -> Page<Dto> 변환.
        return bookDtoList;
    }

    /* 내가 등록한 책 목록 가져오기 */
    public Page<BookDto> myBooks(String uid, String searchTerm, Pageable pageable) {
        Long userId = userRepository.findByUid(uid).getId();
        Page<Book> bookList = bookRepository.findByMyBooks(userId, searchTerm, searchTerm, searchTerm, pageable);
        Page<BookDto> bookDtoList = new BookDto().toDtoList(bookList); // Page<Entity> -> Page<Dto> 변환.
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
