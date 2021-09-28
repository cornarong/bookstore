package com.mytoy.bookstore.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookForm {

    // @NotNull -> 모든타입 - null(X), 공백(O), 빈문자(O)
    // @NotEmpty -> String - null(X), 공백(X), 빈문자(O)
    // @NotBlank -> String - null(X), 공백(X), 빈문자(X)

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
//    @NotNull(message = "필수 항목 입니다.")
    private MultipartFile thumbnail; // 썸네일

}
