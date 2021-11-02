package com.mytoy.bookstore.dto;

import com.mytoy.bookstore.model.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDto {

    private Long id;
    private User user;
    private List<OrderBook> orderBooks;
    private Delivery delivery;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private int totalPrice;

    /* 생성 메소드 */
    public OrderDto createOrderDto(Order order){
        this.id = order.getId();
        this.user = order.getUser();
        this.orderBooks = order.getOrderBooks();
        this.delivery = order.getDelivery();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.totalPrice = order.totalPrice(order.getOrderBooks());
        return this;
    }

}
