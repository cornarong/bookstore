package com.mytoy.bookstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;
    private LocalDateTime regDate;

    /* 장바구니 생성 메소드 */
    public static Basket createBasket(User user, Book book, int quantity){
        Basket basket = Basket.builder()
                .user(user)
                .book(book)
                .quantity(quantity)
                .build();
        return basket;
    }


}
