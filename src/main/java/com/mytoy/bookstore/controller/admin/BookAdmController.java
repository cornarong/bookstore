package com.mytoy.bookstore.controller.admin;

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
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/book")
public class BookAdmController {

    private final BookRepository bookRepository;

    // 아이템 리스트(관리자)
    @GetMapping
    public String list(Model model){
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books",books);
        return "/admin/book/list";
    }

    // 아이템 등록 화면(관리자)
    @GetMapping("/addForm")
    public String addForm(Model model){
        model.addAttribute("bookForm", new BookForm());
        return "admin/book/addForm";
    }

    // 아이템 등록(관리자)
    @PostMapping("/addForm") // BookForm에 MultipartFile 타입의 값을 이미 받았기에 @RequestParam는 필요 없긴한데..
    public String add(@Valid BookForm bookForm, BindingResult bindingResult, @RequestParam MultipartFile thumbnail) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("에러 = {}", bindingResult.getFieldError());
            return"admin/book/addForm";
        }
        Book book = new Book();
        Book newBook = book.createBook(bookForm);
        bookRepository.save(newBook);
        return "redirect:/admin/book";
    }

    // 아이템 상세페이지 화면(관리자)
    @GetMapping("/detail/{bookId}")
    public String detail(@PathVariable Long bookId, Model model){
        Book findBook = bookRepository.findById(bookId).orElse(null);
        model.addAttribute("book",findBook);
        return "admin/book/detail";
    }
}
