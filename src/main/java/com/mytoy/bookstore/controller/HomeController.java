package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;

    @GetMapping
    public String index(Model model,
                        @PageableDefault(size = 12) Pageable pageable,
                        @RequestParam(defaultValue = "") String searchTerm){

            Page<BookDto> bookDtoList = bookService.books(searchTerm, null, "regDate", pageable);
            Page<BookDto> bookDtoListDesc = bookService.books(searchTerm, null, "publishedDate", pageable);

            model.addAttribute("bookDtoList", bookDtoList);
            model.addAttribute("bookDtoListDesc", bookDtoListDesc);
        return "index";
    }


}