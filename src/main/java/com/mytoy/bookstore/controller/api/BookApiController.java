package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookApiController {

    private final BookService bookService;

    /* 책 삭제 */
    @DeleteMapping("/book/{bookId}")
    public void delete(@PathVariable Long bookId){
        bookService.delete(bookId);
    }
}
