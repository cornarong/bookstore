package com.mytoy.bookstore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderBook {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //== 생성 메서드 ==//
    public static OrderBook createOrderItem(Book book, int orderPrice, int count){
        OrderBook orderBook = new OrderBook();
        orderBook.setBook(book);
        orderBook.setOrderPrice(orderPrice);
        orderBook.setCount(count);

        book.removeStock(count);
        return orderBook;
    }

    //== 비즈니스 로직 ==//
    public void cancel() { // 재고수량을 원복 해준다.
        getBook().addStock(count);
    }

    //== 조회 로직 ==//

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = getOrderPrice() * getCount();
        return totalPrice;
    }
}
