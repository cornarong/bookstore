package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private final BookService bookService;

    @GetMapping
    public String store(Model model){
        List<BookDto> BookDtoList = bookService.all();
        model.addAttribute("bookDtoList", BookDtoList);
        return "/store/allList";
    }
}
