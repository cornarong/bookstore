package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class BookApiController {

    @Autowired
    BookService bookService;

    @DeleteMapping
    String delete(){

        return "";
    }

}
