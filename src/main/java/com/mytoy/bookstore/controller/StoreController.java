package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final BookRepository bookRepository;

    @GetMapping
    public String store(){
        return "store/list";
    }

    // 아이템 리스트(관리자)
    @GetMapping("/manageList")
    public String manageItems(){
        return "store/manage/manageList";
    }

    // 아이템 등록 화면(관리자)
    @GetMapping("/addForm")
    public String addForm(Model model){
        model.addAttribute("book", new Book());
        return "store/manage/addForm";
    }
    // 아이템 등록(관리자)
    @PostMapping("/add")
    public String add(@Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return"store/manage/addForm";
        }
        if(book.getDisRate() != 0) book.setDisPrice(book.discountPrice());
        bookRepository.save(book);
        return "redirect:/store/manageList";
    }

    // 아이템 상세페이지 화면(관리자)
    @GetMapping("/manageDetail")
    public String manageDetail(Model model){
        return "store/manage/manageDetail";
    }


}
