package com.mytoy.bookstore.dto;

import com.mytoy.bookstore.model.BookType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Mapper
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private BookType type;      // 타입
    @NotBlank(message = "필수 항목입니다")
    private String title;       // 제목
    @NotBlank(message = "필수 항목입니다")
    private String subTitle;    // 부제목
    @NotBlank(message = "필수 항목입니다")
    private String author;      // 저자
    @NotBlank(message = "필수 항목입니다")
    private String publisher;   // 출판사
    @Min(value = 2000, message = "최소 금액은 2000원 입니다")
    @Max(value = 10000000, message = "최대 금액은 10000000원 입니다")
    private int price;          // 가격
    @Min(value = 1, message = "최소 수량은 1개 입니다")
    @Max(value = 10000, message = "최대 금액은 10000개 입니다")
    private int quantity;       // 수량
    private String content;     // 설명
    private String publishedDate; // 발행일
    private int disRate;        // 할인율
    private int disPrice;       // 할인가
    private int shippingFee;    // 배송비

    /* 첨부파일 */
    private MultipartFile thumbnail; // 책 이미지 객체
    private String thumbnailName; // 책 이미지 파일명
    private String thumbnailPath; // 책 이미지 물리경로
}
