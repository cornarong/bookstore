package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.OrderDto;
import com.mytoy.bookstore.mapper.OrderMapper;
import com.mytoy.bookstore.mapper.OrderMapperImpl;
import com.mytoy.bookstore.model.*;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.OrderRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /* 내 주문 전체 조회 */
    public List<OrderDto> orders(String uid){
        Long userId = userRepository.findByUid(uid).getId();
        List<Order> orders = orderRepository.findAllByUserId(userId);
        log.info("orderBooks 1 > = {}", orders.get(0).getOrderBooks().get(0).getBook().getTitle());
        log.info("orderBooks 2 > = {}", orders.get(1).getOrderBooks().get(0).getBook().getTitle());
        List<OrderDto> orderDtoList = new ArrayList<>();

//        ## Mapstruct 매핑방식 잠시 보류. ##
//        OrderMapper orderMapper = new OrderMapperImpl();
//        OrderDto orderDto = orderMapper.toOrderDto(order);
//        orderDtoList.add(orderDto);

        for(Order order : orders){
            OrderDto orderDto = new OrderDto().createOrderDto(order);
            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }

    /* 주문 */
    @Transactional
    public void order(Long bookId, String uid, int count){
        User user = userRepository.findByUid(uid);
        Book book = bookRepository.findById(bookId).orElse(null);
        // 배송 생성
        Delivery delivery = Delivery.builder().status(DeliveryStatus.READY).build();
        delivery.saveAddress(user.getAddress());
        // 주문/책 생성
        OrderBook orderBook = OrderBook.createOrderBook(book, book.getDisPrice(), count);
        // 주문 생성
        Order order = Order.createOrder(user, delivery, orderBook);

        orderRepository.save(order);
    }

    /* 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findById(orderId).orElse(null);
        // 주문 취소
        order.cancel();
    }
}
