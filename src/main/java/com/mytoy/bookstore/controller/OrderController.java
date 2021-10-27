package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.OrderRepository;
import com.mytoy.bookstore.service.BookService;
import com.mytoy.bookstore.service.OrderService;
import com.mytoy.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;
    private final UserService userService;

    @PostMapping("/{bookId}")
    public String order(@PathVariable Long bookId, Authentication authentication, @RequestParam int cnt){
        String uid = authentication.getName();

        System.out.println(bookId);
        System.out.println(uid);
        System.out.println(cnt);

        //Long order = orderService.order(bookId, uid, 10);

        return "/user/orderForm";
    }

}
