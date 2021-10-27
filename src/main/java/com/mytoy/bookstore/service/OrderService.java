package com.mytoy.bookstore.service;

import com.mytoy.bookstore.model.*;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.OrderRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /**
     * 주문
     */
    @Transactional(readOnly = false)
    public Long order(Long bookId, String uid, int count){
        // 엔티티 조회
        User user = userRepository.findByUid(uid);
        Book book = bookRepository.findById(bookId).orElse(null);
        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        // 주문상품 생성
        OrderBook orderBook = OrderBook.createOrderItem(book, book.getPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(user, delivery, orderBook);
        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional(readOnly = false)
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElse(null);
        // 주문 취소
        order.cancel();
    }
}
