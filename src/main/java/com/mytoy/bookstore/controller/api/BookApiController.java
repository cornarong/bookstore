package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class BookApiController {

    @Autowired
    BookService bookService;


    /* 책 삭제 */
    @DeleteMapping("/book/{bookId}")
    public void delete(@PathVariable Long bookId){
        bookService.delete(bookId);
    }
}
