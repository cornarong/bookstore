package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.repository.OrderRepository;
import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    @DeleteMapping("/order/{orderId}")
    public void delete(@PathVariable Long orderId){

        orderService.

    }
}
