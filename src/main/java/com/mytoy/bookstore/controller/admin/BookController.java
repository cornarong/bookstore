package com.mytoy.bookstore.controller.admin;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BookController {

    private final BookService bookService;

    /* 책 목록 */
    @GetMapping("/book")
        public String list(Model model){
        List<BookDto> bookDtoList = bookService.list();
        model.addAttribute("bookDtoList", bookDtoList);
        return "/admin/book/list";
    }

    /* 책 등록 페이지 */
    @GetMapping("/book/addForm")
    public String addForm(Model model){
        model.addAttribute("bookDto", new BookDto());
        return "admin/book/addForm";
    }

    /* 책 등록 */
    @PostMapping("/book/addForm")
    public String add(@Valid BookDto bookDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return"admin/book/addForm";
        }
        bookService.add(bookDto);
        // 상세 페이지로 이동하도록 수정. + 저장되엇씁니다 파라미터 처리. ture
        return "redirect:/admin/book";
    }

    /* 책 상세 페이지 */
    @GetMapping("/book/{bookId}")
    public String detail(@PathVariable Long bookId, Authentication authentication, Model model){
        BookDto bookDto = bookService.detail(bookId);
        String uid = authentication.getName();
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("auth_uid", uid);
        return "/admin/book/detailForm";
    }

    /* 책 수정 페이지 */
    @GetMapping("/book/edit/{bookId}")
    public String editForm(@PathVariable Long bookId, Model model){
        BookDto bookDto = bookService.detail(bookId);
        model.addAttribute("bookDto", bookDto);
        return "/admin/book/editForm";
    }

    /* 책 수정 */
    @PutMapping("/book/edit/{bookId}")
    public String edit(@PathVariable Long bookId, @Valid BookDto bookDto, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "/admin/book/editForm";
        }
        bookService.edit(bookId, bookDto);
        return "redirect:/admin/book/" + bookId;
    }
 }
