package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    /* 주문 취소 하기 */
    @PutMapping("/order/{orderId}")
    public void edit(@PathVariable Long orderId){
        log.info("orderId >>> {}", orderId);

        orderService.edit(orderId);
    }

    /* 내역 삭제 하기 */
    @DeleteMapping("/order/{orderId}")
    public void delete(@PathVariable Long orderId){

        orderService.delete(orderId);
    }

}
