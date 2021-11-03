package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.BasketDto;
import com.mytoy.bookstore.model.Basket;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BasketRepository;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BasketRepository basketRepository;

    /* 장바구니 조회 */
    public List<BasketDto> all(String uid){
        Long userId = userRepository.findByUid(uid).getId();
        List<Basket> basketList = basketRepository.findAllByUserId(userId);
        List<BasketDto> basketDtoList = new ArrayList<>();
        for(Basket basket : basketList){
            BasketDto basketDto = BasketDto.createBasketDto(basket);
            basketDtoList.add(basketDto);
        }
        return basketDtoList;
    }

    /* 장바구니 담기 */
    @Transactional
    public void addBasket(Long bookId, String uid, int quantity){
        User user = userRepository.findByUid(uid);
        Book book = bookRepository.findById(bookId).orElse(null);

        Basket basket = Basket.createBasket(user, book, quantity);

        basketRepository.save(basket);
    }

    /* 장바구니 삭제*/
    @Transactional
    public void delete(Long basketId){
        basketRepository.deleteById(basketId);
    }

    /* 장바구니 전체 삭제*/
    @Transactional
    public void deleteAll(String uid){
        Long userId = userRepository.findByUid(uid).getId();
        basketRepository.deleteAllByUserId(userId);
    }

    /* 총 합계 금액 */
    public int totalPrice(List<BasketDto> basketDtoList){
        int totalPrice = 0;
        for(BasketDto basketDto : basketDtoList){
            totalPrice += (basketDto.getDisPrice() * basketDto.getQuantity());
        }
        return totalPrice;
    }

}
