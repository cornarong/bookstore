package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.form.BookForm;
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
        model.addAttribute("bookForm", new BookForm());
        return "store/manage/addForm";
    }
    // 아이템 등록(관리자)
    @PostMapping("/add")
    public String add(@Valid BookForm bookForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return"store/manage/addForm";
        }

        Book book = new Book();
        Book newBook = book.createBook(bookForm);

        System.out.println("newBook.getTitle() = " + newBook.getTitle());
        System.out.println("newBook.getSubTitle() = " + newBook.getSubTitle());
        System.out.println("newBook.getAuthor() = " + newBook.getAuthor());
        System.out.println("newBook.getQuantity() = " + newBook.getQuantity());
        System.out.println("newBook.getPublisher() = " + newBook.getPublisher());
        System.out.println("newBook.getPublishedDate() = " + newBook.getPublishedDate());
        System.out.println("newBook.getPrice() = " + newBook.getPrice());
        System.out.println("newBook.getDisRate() = " + newBook.getDisRate());
        System.out.println("newBook.getDisPrice() = " + newBook.getDisPrice());
        System.out.println("newBook.getShippingFee() = " + newBook.getShippingFee());
        System.out.println("newBook.getImage() = " + newBook.getImage());
        System.out.println("newBook.getContent() = " + newBook.getContent());

        bookRepository.save(newBook);
        return "redirect:/store/manageList";
    }

    // 아이템 상세페이지 화면(관리자)
    @GetMapping("/manageDetail")
    public String manageDetail(Model model){
        return "store/manage/manageDetail";
    }
}
