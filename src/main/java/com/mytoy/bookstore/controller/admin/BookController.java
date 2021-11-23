package com.mytoy.bookstore.controller.admin;

import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@Secured({"ROLE_ADMIN","ROLE_MANAGER"}) // 해당 권한만 접근 가능
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /* 책관리 목록 */
    @GetMapping("/book")
        public String list(Model model, Authentication authentication,
                           @PageableDefault(size = 10) Pageable pageable,
                           @RequestParam(defaultValue = "") String searchTerm){

        Page<BookDto> bookDtoList;

        if(isAdmin(authentication)){
            bookDtoList = bookService.books(searchTerm, null, "regDate", pageable);
        }else {
            bookDtoList = bookService.myBooks(authentication.getName(), searchTerm, pageable);
        }

        int startPage = Math.max(1, bookDtoList.getPageable().getPageNumber() - 10);
        int endPage = Math.min(bookDtoList.getTotalPages(), bookDtoList.getPageable().getPageNumber() + 10);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("bookDtoList", bookDtoList);
        return "admin/book/list";
    }

    /* 책관리 등록페이지 */
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
            return "admin/book/addForm";
        }

        String uid = authentication.getName();
        Long bookId = bookService.add(bookDto, uid);

        redirectAttributes.addAttribute("save", true);
        return "redirect:/book/" + bookId;
    }

    /* 책관리 상세페이지 */
    @GetMapping("/book/{bookId}")
    public String detail(@PathVariable Long bookId, Authentication authentication, Model model){
        BookDto bookDto = bookService.detail(bookId);
        String uid = authentication.getName();

        // 관리자 권한 없음 && 직접 등록한 책 아님
        if(!isAdmin(authentication) && !isRegistrant(bookDto, authentication)) {
            return "redirect:/book";
        }

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("auth_uid", uid);
        return "admin/book/detail";
    }

    /* 책관리 수정페이지 */
    @GetMapping("/book/edit/{bookId}")
    public String editForm(@PathVariable Long bookId, Model model, Authentication authentication){
        BookDto bookDto = bookService.detail(bookId);

        // 관리자 권한 없음 && 직접 등록한 책 아님
        if(!isAdmin(authentication) && !isRegistrant(bookDto, authentication)) {
            return "redirect:/book";
        }

        model.addAttribute("bookDto", bookDto);
        return "admin/book/editForm";
    }

    /* 책정보 수정 */
    @PutMapping("/book/edit/{bookId}")
    public String edit(@PathVariable Long bookId, @Valid BookDto bookDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {

        // 관리자 권한 없음 && 직접 등록한 책 아님
        if(!isAdmin(authentication) && !isRegistrant(bookDto, authentication)) {
            return "redirect:/book";
        }

        if(bindingResult.hasErrors()){
            return "admin/book/editForm";
        }

        bookService.edit(bookId, bookDto);

        redirectAttributes.addAttribute("edit", true);
        return "redirect:/book/" + bookId;
    }

    /* 관리자 권한 확인 메서드 */
    public static Boolean isAdmin(Authentication authentication){
        if(authentication.getAuthorities().toString().contains("ROLE_ADMIN")){
            return true;
        }
        return false;
    }

    /* 도서 등록자 확인 메서드 */
    public static Boolean isRegistrant(BookDto bookDto, Authentication authentication){
        if(bookDto.getUid().equals(authentication.getName())){
            return true;
        }
        return false;
    }
 }
