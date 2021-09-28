package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.form.BookForm;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
        model.addAttribute("bookForm", new BookForm());
        return "store/manage/addForm";
    }
    // 아이템 등록(관리자)
    @PostMapping("/add") // BookForm에 MultipartFile 타입의 값을 이미 받았기에 @RequestParam는 필요 없긴한데..
    public String add(@Valid BookForm bookForm, BindingResult bindingResult, @RequestParam MultipartFile thumbnail) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("에러 = {}", bindingResult.getFieldError());
            return"store/manage/addForm";
        }

        Book book = new Book();
        Book newBook = book.createBook(bookForm);

        log.info("newBook.getTitle() = " + newBook.getTitle());
        log.info("newBook.getSubTitle() = " + newBook.getSubTitle());
        log.info("newBook.getAuthor() = " + newBook.getAuthor());
        log.info("newBook.getQuantity() = " + newBook.getQuantity());
        log.info("newBook.getPublisher() = " + newBook.getPublisher());
        log.info("newBook.getPublishedDate() = " + newBook.getPublishedDate());
        log.info("newBook.getPrice() = " + newBook.getPrice());
        log.info("newBook.getDisRate() = " + newBook.getDisRate());
        log.info("newBook.getDisPrice() = " + newBook.getDisPrice());
        log.info("newBook.getShippingFee() = " + newBook.getShippingFee());
        log.info("newBook.getContent() = " + newBook.getContent());
        log.info("파일명 = {}", newBook.getThumbnail());
        log.info("파일 저장 경로 = {}", newBook.getThumbnailPath());

        bookRepository.save(newBook);

        return "redirect:/store/manageList";
    }

    // 아이템 상세페이지 화면(관리자)
    @GetMapping("/manageDetail")
    public String manageDetail(Model model){
        return "store/manage/manageDetail";
    }
}
