package com.mytoy.bookstore.controller.api;


import com.mytoy.bookstore.service.BasketService;
import com.mytoy.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BasketApiController {

    private final BasketService basketService;
    private final OrderService orderService;

    /* 장바구니 담기 */
    @PostMapping("/basket/{bookId}")
    public void addBasket(@PathVariable Long bookId, @RequestParam("cnt") int quantity, Authentication authentication){
        String uid = authentication.getName();

        basketService.addBasket(bookId, uid, quantity);
    }

    /* 장바구니 삭제 */
    @DeleteMapping("/basket/{basketId}")
    void delete(@PathVariable Long basketId) {
        basketService.delete(basketId);
    }

    /* 장바구니 주문 하기 */
    @PostMapping("/basket/order")
    public void basketOrder(Authentication authentication){
        String uid = authentication.getName();

        orderService.basketOrder(uid);
        basketService.deleteAll(uid);
    }
}