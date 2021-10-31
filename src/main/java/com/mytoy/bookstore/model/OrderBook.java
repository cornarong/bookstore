package com.mytoy.bookstore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderBook {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    public void saveOrder(Order order){
        this.order = order;
    }

    /* 생성 메서드 */
    public static OrderBook createOrderBook(Book book, int orderPrice, int count){
        OrderBook orderBook = OrderBook.builder()
                .book(book)
                .orderPrice(orderPrice)
                .count(count)
                .build();
        book.removeStock(count);
        return orderBook;
    }

    public void cancel() { // 재고수량을 원복 해준다.
        getBook().addStock(count);
    }


    /*  주문상품 전체 가격 조회  */
    public int getTotalPrice() {
        int totalPrice = getOrderPrice() * getCount();
        return totalPrice;
    }
}
