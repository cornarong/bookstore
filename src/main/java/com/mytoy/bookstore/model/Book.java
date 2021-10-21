package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.exception.NotEnoughStockException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;

@Entity
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BookType type;      // 타입
    private String title;       // 제목
    private String subTitle;    // 부제목
    private String content;     // 설명
    private String author;      // 저자
    private String publisher;   // 출판사
    private String publishedDate; // 발행일
    private int price;          // 가격
    private int disRate;        // 할인율
    private int disPrice;       // 할인가
    private int quantity;       // 수량
    private int shippingFee;    // 배송비
    private String thumbnailName; // 섬네일
    private String thumbnailPath; // 섬네일 물리 경로


    // 잠시 대기.. DB에 사용자ID를 외래키로 같이 들어가야함.
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * 비즈니스 로직
     * 객체 지향에 가깝게 직접 엔티디에 설계함으로써 관리하기에도 편한다.
     */

    /* 책 이미지 저장 */
    public void saveThumbnail(MultipartFile thumbnail) throws IOException {
        if(thumbnail.getSize() != 0 || !thumbnail.getOriginalFilename().equals("")){
            String baseDir = "D:\\study\\profile_image"; // 현재 회원 프로필 물리경로와 같은 경로를 사용중..(임시)
            String filePath = baseDir + "\\" + thumbnail.getOriginalFilename();
            thumbnail.transferTo(new File(filePath));
            this.thumbnailName = thumbnail.getOriginalFilename();
            this.thumbnailPath = filePath;
        }else{
            this.thumbnailName = null;
            this.thumbnailPath = null;
        }
    }

    /* 책 정보 수정 */
    public void edit(BookDto bookDto) throws IOException {
        if(bookDto.getType().toString() == "DOMESTIC") this.type = BookType.DOMESTIC;
        if(bookDto.getType().toString() == "INTERNATIONAL") this.type = BookType.INTERNATIONAL;
        this.title = bookDto.getTitle();
        this.content = bookDto.getContent();
        this.subTitle = bookDto.getSubTitle();
        this.quantity = bookDto.getQuantity();
        this.publisher = bookDto.getPublisher();
        this.publishedDate = bookDto.getPublishedDate();
        this.price = bookDto.getPrice();
        this.disRate = bookDto.getDisRate();
        this.disPrice = bookDto.getDisPrice();
        this.shippingFee = bookDto.getShippingFee();
        if(bookDto.getThumbnail().getSize() != 0){
            this.saveThumbnail(bookDto.getThumbnail());
        }else{
            this.thumbnailName = bookDto.getThumbnailName();
            this.thumbnailPath = bookDto.getThumbnailPath();
        }
        System.out.println(this.getThumbnailName());
        System.out.println(this.getThumbnailPath());
    }

    /* stock(재고) 증가 */
    public void addStock(int quantity){
        this.quantity += quantity;
    }

    /* stock(재고) 감소 */
    public void removeStock(int quantity){
        int restStock = this.quantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.quantity = restStock;
    }
}
