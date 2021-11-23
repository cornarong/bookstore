package com.mytoy.bookstore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "orders")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    private Long id;

    // 연관관계의 주인, 반대쪽은 mappedBy 설정 해준다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // OneToOne의 경우는 연관관계 주인을 아무나 가져도 된다.
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간 (hibernate 지원)

    // enum 타입은 @Enumerated 꼭 넣어야 한다. 기본은 ORDINAL으로 DB에 숫자로 들어간다. 중간에 값이 추가되면 문제가 발생한다.(순서밀림) 꼭 STRING으로 쓰자.
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //== 양방향 연관관계 메서드 ==// : 양방향 관계 일 때 셋팅해주면 한쪽에서 처리가 가능하도록 해준다.
    // ex) member과 order의 연관관계 메서드, setMember하나 order처리와 동시에 내부에서 member도 처리해준다.
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    /* 생성 메서드 */
    public static Order createOrder(User user, Delivery delivery, OrderBook... orderBooks){
        Order order = Order.builder()
                .user(user)
                .delivery(delivery)
                .orderBooks(new ArrayList<>())
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();
        for(OrderBook orderBook : orderBooks){
            order.orderBooks.add(orderBook);
            orderBook.saveOrder(order);
        }
        delivery.saveOrder(order);
        return order;
    }

    /**
     * 비즈니스 로직
     */

    /* 주문 취소 */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }
        this.status = OrderStatus.CANCEL;
        for(OrderBook orderBook : orderBooks){
            orderBook.cancel();
        }
    }

    /* 주문 합계 금액 */
    public int totalPrice(List<OrderBook> orderBooks) {
        int totalPrice = 0;
        for(OrderBook orderBook : orderBooks){
            totalPrice += orderBook.totalPrice();
        }
        return totalPrice;
    }
}
