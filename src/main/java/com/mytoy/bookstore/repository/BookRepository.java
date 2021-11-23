package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.BookType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Book findByTitle(String title);

    // 내가 등록한 도서 (검색 가능 : 제목, 작가, 출판사)
    @Query("select b from Book b where b.user.id = ?1 and (b.title like %?2% or b.author like %?2% or b.publisher like %?2%)")
    Page<Book> myBooks(Long userId, String searchTerm, Pageable pageable);

    // 모든도서 발행일 내림차순 정렬 (검색 가능 : 제목, 작가, 출판사)
    @Query("select b from Book b where b.title like %?1% or b.author like %?1% or b.publisher like %?1% order by b.publishedDate desc")
    Page<Book> booksOrderByPublishedDate(String searchTerm, Pageable pageable);

    // 모든도서 등록일 내림차순 정렬 (검색 가능 : 제목, 작가, 출판사)
    @Query("select b from Book b where b.title like %?1% or b.author like %?1% or b.publisher like %?1% order by b.regDate desc")
    Page<Book> booksOrderByRegDate(String searchTerm, Pageable pageable);

    // 국내도서, 외국도서 (검색 가능 : 제목, 작가, 출판사)
    @Query("select b from Book b where b.type = ?2 and (b.title like %?1% or b.author like %?1% or b.publisher like %?1%)")
    Page<Book> booksSortBy(String searchTerm, BookType bookType, Pageable pageable);
}
