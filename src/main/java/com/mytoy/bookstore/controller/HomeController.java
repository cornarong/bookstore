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
            Page<BookDto> bookDtoList = bookService.all(searchTerm, null, pageable);
            Page<BookDto> bookDtoListDesc = bookService.allDesc(searchTerm, pageable, "publishedDate");

            model.addAttribute("bookDtoList", bookDtoList);
            model.addAttribute("bookDtoListDesc", bookDtoListDesc);
        return "index";
    }


}