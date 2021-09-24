package com.mytoy.bookstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title; // 제목
    private String subTitle; // 부제목
    private String content; // 설명
    private String author; // 저자
    private String publisher; // 출판사
    private String publishedDate; // 발행일
    private int price; // 가격
    private int disRate; // 할인율
    private int disPrice; // 할인가
    private int quantity; // 수량
    private int shippingFee; // 배송비
    private String imageUrl; // 이미지

    // 잠시 보류 클라이언트사이드에서 처리할 것. 스크립트로
    public int discountPrice(){
        return price - (price * (disRate/100));
    }

}
