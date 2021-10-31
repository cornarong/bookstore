package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/{bookId}")
    public String order(@PathVariable Long bookId, Authentication authentication, @RequestParam int cnt){
        String uid = authentication.getName();
        orderService.order(bookId, uid, cnt);

        return "redirect:/user/order";
    }

}
