package com.mytoy.bookstore.controller;


import com.mytoy.bookstore.dto.BasketDto;
import com.mytoy.bookstore.repository.BasketRepository;
import com.mytoy.bookstore.service.BasketService;
import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;
    private final OrderService orderService;

    /* 장바구니 페이지 */
    @GetMapping
    public String basket(Model model, Authentication authentication){
        String uid = authentication.getName();

        List<BasketDto> basketDtoList = basketService.all(uid);
        int totalPrice = basketService.totalPrice(basketDtoList);

        model.addAttribute("basketDtoList", basketDtoList);
        model.addAttribute("totalPrice", totalPrice);
        return "/user/basketForm";
    }

    /* 장바구니 주문 하기 */
    @PostMapping
    public String basketOrder(Authentication authentication){
        String uid = authentication.getName();

        orderService.basketOrder(uid);
        basketService.deleteAll(uid);

        return "redirect:/order";
    }

}
