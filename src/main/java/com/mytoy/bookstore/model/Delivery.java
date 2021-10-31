package com.mytoy.bookstore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded // 내장타입 선언
    private Address address;

    // enum 타입은 @Enumerated 꼭 넣어야 한다. 기본은 ORDINAL으로 DB에 숫자로 들어간다. 중간에 값이 추가되면 문제가 발생한다.(순서밀림) 꼭 STRING으로 쓰자.
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP


    /* 배송정보 생성 */
    public void saveAddress(Address address){
        this.address = address;
    }

    /* Order엔티티 생성 */
    public void saveOrder(Order order){
        this.order = order;
    }


}
