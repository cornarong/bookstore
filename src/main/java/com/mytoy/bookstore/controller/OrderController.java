package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.OrderDto;
import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /* 주문내역 페이지 */
    @GetMapping
    public String order(Model model, Authentication authentication){
        String uid = authentication.getName();

        List<OrderDto> orderDtoList = orderService.orders(uid);
        model.addAttribute("orderDtoList", orderDtoList);
        return "/user/orderForm";
    }

    /* 단건 주문 하기 */
    @PostMapping("/{bookId}")
    public String order(@PathVariable Long bookId, Authentication authentication, @RequestParam int cnt){
        String uid = authentication.getName();
        orderService.order(bookId, uid, cnt);

        return "redirect:/order";
    }

}
