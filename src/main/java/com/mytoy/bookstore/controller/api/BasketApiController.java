package com.mytoy.bookstore.controller.api;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.repository.BasketRepository;
import com.mytoy.bookstore.service.BasketService;
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

    /* 장바구니 담기 */
    @PostMapping("/basket/{bookId}")
    public void addBasket(@PathVariable Long bookId, Authentication authentication, @RequestParam("cnt") int quantity){
        String uid = authentication.getName();
        basketService.addBasket(bookId, uid, quantity);
    }

    /* 장바구니 삭제 */
    @DeleteMapping("/basket/{basketId}")
    void delete(@PathVariable Long basketId) {
        basketService.delete(basketId);
    }

}