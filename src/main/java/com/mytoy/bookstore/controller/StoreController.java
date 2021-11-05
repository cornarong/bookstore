package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.BasketDto;
import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.BookType;
import com.mytoy.bookstore.service.BasketService;
import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final BookService bookService;
    private final BasketService basketService;

    /* 모든도서 페이지 , 국내도서 페이지, 외국도서 페이지*/
    @GetMapping
    public String store(Model model,
                        @PageableDefault(size = 10) Pageable pageable,
                        @RequestParam(defaultValue = "") String type,
                        @RequestParam(defaultValue = "") String searchTerm){
        BookType bookType = null;
        if(type.equals("K")){
            bookType = BookType.DOMESTIC;
        }else if(type.equals("F")){
            bookType = BookType.INTERNATIONAL;
        }

        Page<BookDto> bookDtoList = bookService.all(searchTerm, bookType, pageable);

        int startPage = Math.max(1, bookDtoList.getPageable().getPageNumber() - 10);
        int endPage = Math.min(bookDtoList.getTotalPages(), bookDtoList.getPageable().getPageNumber() + 10);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("bookDtoList", bookDtoList);
        model.addAttribute("type", bookType == null ? "all" : bookType == BookType.DOMESTIC ? "korea" : "foreign" );
        return "/store/allList";
    }

    /* 책정보 상세페이지 */
    @GetMapping("/{bookId}")
    public String detail(@PathVariable Long bookId, Model model, Authentication authentication){
        String uid = authentication.getName();

        BookDto bookDto = bookService.detail(bookId);
        List<BasketDto> basketDtoList = basketService.all(uid);
        int totalPrice = basketService.totalPrice(basketDtoList);

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("basketDtoList", basketDtoList);
        model.addAttribute("totalPrice", totalPrice);
        return "/store/detail";
    }
}
