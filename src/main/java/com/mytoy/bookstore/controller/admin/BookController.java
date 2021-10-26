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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<BookDto> bookDtoList = bookService.all();

        model.addAttribute("bookDtoList", bookDtoList);

        return "/admin/book/list";
    }

    /* 책 등록페이지 */
    @GetMapping("/book/addForm")
    public String addForm(Model model){
        BookDto bookDto = new BookDto();

        model.addAttribute("bookDto", bookDto);

        return "admin/book/addForm";
    }

    /* 책 등록 */
    @PostMapping("/book/addForm")
    public String add(@Valid BookDto bookDto, BindingResult bindingResult, Authentication authentication,
                      RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult.getFieldError());
            return "admin/book/addForm";
        }
        String uid = authentication.getName();
        Long bookId = bookService.add(bookDto, uid);

        redirectAttributes.addAttribute("save", true);

        return "redirect:/admin/book/" + bookId;
    }

    /* 책정보 상세페이지 */
    @GetMapping("/book/{bookId}")
    public String detail(@PathVariable Long bookId, Authentication authentication, Model model){
        BookDto bookDto = bookService.detail(bookId);
        String uid = authentication.getName();

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("auth_uid", uid);

        return "/admin/book/detailForm";
    }

    /* 책정보 수정페이지 */
    @GetMapping("/book/edit/{bookId}")
    public String editForm(@PathVariable Long bookId, Model model){
        BookDto bookDto = bookService.detail(bookId);

        model.addAttribute("bookDto", bookDto);

        return "/admin/book/editForm";
    }

    /* 책정보 수정 */
    @PutMapping("/book/edit/{bookId}")
    public String edit(@PathVariable Long bookId, @Valid BookDto bookDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult.getFieldError());
            return "/admin/book/editForm";
        }
        bookService.edit(bookId, bookDto);

        redirectAttributes.addAttribute("edit", true);

        return "redirect:/admin/book/" + bookId;
    }
 }
