package com.mytoy.bookstore.dto;

import com.mytoy.bookstore.mapper.BookMapper;
import com.mytoy.bookstore.mapper.BookMapperImpl;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.BookType;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDto {

    private Long id;            // ID
    private BookType type;      // 타입 : 국내, 외국
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
    @Max(value = 10000, message = "최대 수량은 10000개 입니다")
    private int quantity;       // 수량
    private String content;     // 설명
    @NotBlank(message = "필수 항목입니다")
    private String publishedDate; // 발행일
    private String regDate;       // 등록일
    private int disRate;        // 할인율
    private int disPrice;       // 할인가
    private int shippingFee;    // 배송비
    private String uid;         // 등록자

    private MultipartFile thumbnail; // 책 이미지 객체
    private String thumbnailType; // 책 이미지 타입(파일 or url)
    private String thumbnailPath; // 책 이미지 물리경로

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public Page<BookDto> toDtoList(Page<Book> bookList){
        Page<BookDto> bookDtoList = bookList.map(m -> BookDto.builder()
                .id(m.getId())
                .uid(m.getUser().getUid())
                .type(m.getType())
                .title(m.getTitle())
                .subTitle(m.getSubTitle())
                .content(m.getContent())
                .author(m.getAuthor())
                .publisher(m.getPublisher())
                .publishedDate(String.valueOf(m.getPublishedDate()))
                .regDate(String.valueOf(m.getRegDate()))
                .price(m.getPrice())
                .disRate(m.getDisRate())
                .disPrice(m.getDisPrice())
                .quantity(m.getQuantity())
                .shippingFee(m.getShippingFee())
                .thumbnailType(m.getThumbnailType())
                .thumbnailPath(m.getThumbnailPath())
                .build());
        return bookDtoList;
    }

    /* 첨부파일 기본값 처리 */
    public void defaultThumbnail(){ // DB의 이미지 값이 'NULL' 일 경우 보여지는 DTO 기본값 처리.
        this.thumbnailType = "noImage.jpg";
        this.thumbnailPath = "";
    }
}
