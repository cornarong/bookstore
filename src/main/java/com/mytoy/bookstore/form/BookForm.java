package com.mytoy.bookstore.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookForm {

    // @NotEmpty는 String 타입에 사용하는 애노테이션이다.
    // int 타입은 @NotNull을 사용한다.

    private Long id;
    @NotBlank(message = "필수 항목입니다.")
    private String title; // 제목
    private String subTitle; // 부제목
    private String content; // 설명
    @NotBlank(message = "필수 항목입니다.")
    private String author; // 저자
    @NotBlank(message = "필수 항목입니다.")
    private String publisher; // 출판사
    private String publishedDate; // 발행일
    @NotNull(message = "바보냐?")
    @Min(value = 1000, message = "최소금액은 1000원입니다.")
    private int price; // 가격
    private int disRate; // 할인율
    private int disPrice; // 할인가
    @NotNull(message = "바보냐?")
    @Min(value = 1, message = "최소수량은 1개입니다.")
    private int quantity; // 수량
    private int shippingFee; // 배송비
    @NotBlank(message = "필수 항목 입니다.")
    private String image; // 이미지

}
