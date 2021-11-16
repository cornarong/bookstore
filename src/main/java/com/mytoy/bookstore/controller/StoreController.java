package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.BasketDto;
import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.model.BookType;
import com.mytoy.bookstore.service.BasketService;
import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        Page<BookDto> bookDtoList;
        if(type.equals("K")){
            bookDtoList = bookService.all(searchTerm, BookType.DOMESTIC, pageable);
        }else if(type.equals("F")){
            bookDtoList = bookService.all(searchTerm, BookType.INTERNATIONAL, pageable);
        }else if(type.equals("D")){
            bookDtoList = bookService.allDesc(searchTerm, pageable, "publishedDate");
        }else{
            bookDtoList = bookService.allDesc(searchTerm, pageable, "regDate");
        }

        int startPage = Math.max(1, bookDtoList.getPageable().getPageNumber() - 10);
        int endPage = Math.min(bookDtoList.getTotalPages(), bookDtoList.getPageable().getPageNumber() + 10);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("bookDtoList", bookDtoList);
        model.addAttribute("type", (type.equals("") || type.equals("A")) ? "A" : type.equals("K") ? "K" : type.equals("F") ? "F" : "D");
        return "store/allList";
    }

    /* 책정보 상세페이지 */
    @GetMapping("/{bookId}")
    public String detail(@PathVariable Long bookId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = authentication.getName();

        BookDto bookDto = bookService.detail(bookId);

        if(!uid.equals("anonymousUser")){
            List<BasketDto> basketDtoList = basketService.all(uid);
            int totalPrice = basketService.totalPrice(basketDtoList);
            model.addAttribute("basketDtoList", basketDtoList);
            model.addAttribute("totalPrice", totalPrice);
        }else{
            model.addAttribute("basketDtoList", new ArrayList<BasketDto>());
            model.addAttribute("totalPrice", 0);
        }

        model.addAttribute("bookDto", bookDto);
        return "store/detail";
    }
}
