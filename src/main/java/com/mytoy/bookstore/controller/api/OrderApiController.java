package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderApiController {

    private final OrderService orderService;

    /* 단건 주문 하기 */
    @PostMapping("/order/{bookId}")
    public void order(@PathVariable Long bookId, Authentication authentication, @RequestParam int cnt){
        String uid = authentication.getName();

        orderService.order(bookId, uid, cnt);
    }

    /* 주문 취소 하기 */
    @PutMapping("/order/{orderId}")
    public void edit(@PathVariable Long orderId){

        orderService.edit(orderId);
    }

    /* 내역 삭제 하기 */
    @DeleteMapping("/order/{orderId}")
    public void delete(@PathVariable Long orderId){

        orderService.delete(orderId);
    }
}
