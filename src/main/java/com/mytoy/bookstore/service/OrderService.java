package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.OrderDto;
import com.mytoy.bookstore.mapper.OrderMapper;
import com.mytoy.bookstore.mapper.OrderMapperImpl;
import com.mytoy.bookstore.model.*;
import com.mytoy.bookstore.repository.BasketRepository;
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
    private final BasketRepository basketRepository;

    /* 주문내역 전체 조회 */
    public List<OrderDto> orders(String uid){
        Long userId = userRepository.findByUid(uid).getId();
        List<Order> orders = orderRepository.findAllByUserId(userId);
        List<OrderDto> orderDtoList = new ArrayList<>();

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
        OrderBook orderBook = OrderBook.createOrderBook(book, count);
        // 주문 생성
        Order order = Order.createOrder(user, delivery, orderBook);

        orderRepository.save(order);
    }

    /* 장바구니 주문 */
    @Transactional
    public void basketOrder(String uid){
        User user = userRepository.findByUid(uid);
        Long userId = user.getId();
        // 배송 생성
        Delivery delivery = Delivery.builder().status(DeliveryStatus.READY).build();
        delivery.saveAddress(user.getAddress());

        List<Basket> basketList = basketRepository.findAllByUserId(user.getId());

        // 주문/책 생성
        OrderBook[] orderBooks = new OrderBook[basketList.size()];
        for(int i=0; i<basketList.size(); i++){
            orderBooks[i] = OrderBook.createOrderBook(basketList.get(i).getBook(), basketList.get(i).getQuantity());
        }

        // 주문 생성
        Order order = Order.createOrder(user, delivery, orderBooks);
        orderRepository.save(order);
    }

    /* 주문 취소 하기 */
    @Transactional
    public void edit(Long orderId){
        Order order = orderRepository.findById(orderId).orElse(null);
        order.cancel();
    }

    /* 내역 삭제 하기 */
    @Transactional
    public void delete(Long orderId){
        orderRepository.deleteById(orderId);
    }
}
