package com.mytoy.bookstore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private com.mytoy.bookstore.model.Order order;

    @Embedded // 내장타입 선언
    private Address address;

    // enum 타입은 @Enumerated 꼭 넣어야 한다. 기본은 ORDINAL으로 DB에 숫자로 들어간다. 중간에 값이 추가되면 문제가 발생한다.(순서밀림) 꼭 STRING으로 쓰자.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
