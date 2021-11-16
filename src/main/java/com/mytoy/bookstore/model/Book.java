package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.dto.BookDto;
import com.mytoy.bookstore.exception.NotEnoughStockException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookType type;      // 타입

    private String title;       // 제목
    private String subTitle;    // 부제목
    private String content;     // 설명
    private String author;      // 저자
    private String publisher;   // 출판사
    private LocalDate publishedDate; // 발행일
    private LocalDate regDate;       // 등록일
    private int price;          // 가격
    private int disRate;        // 할인율
    private int disPrice;       // 할인가
    private int quantity;       // 수량
    private int shippingFee;    // 배송비
    private String thumbnailType; // 책 이미지 타입(파일 or url)
    private String thumbnailPath; // 책 이미지 물리 경로

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;      // 책 등록자

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Basket> baskets = new ArrayList<>();

    /**
     * 비즈니스 로직
     * 객체 지향에 가깝게 직접 엔티디에 설계함으로써 관리하기에도 편한다.
     */

    /* 생성자 메소드 */
    public static Book createBook(BookType bookType, String title, String subTitle, String author, String publisher, LocalDate publishedDate,
                              int price, int disRate, int disPrice, String imageUrl, String content, User user){
        Book book = Book.builder()
                .type(bookType)
                .title(title)
                .subTitle(subTitle)
                .author(author)
                .publisher(publisher)
                .publishedDate(publishedDate)
                .regDate(LocalDate.now())
                .price(price)
                .disRate(disRate)
                .disPrice(disPrice)
                .quantity((int)(Math.random()*(200 - 50)) + 50)
                .shippingFee(0)
                .thumbnailType(imageUrl)
                .content(content)
                .user(user)
                .build();
        return book;
    }
    /* 책 등록자 저장 */
    public void registrant(Book book, User user){
        this.user = user;
    }

    /* 책 등록일 저장 */
    public void regDate(Book book){
        book.regDate = LocalDate.now();
    }

    /* 책 이미지 저장 */
    public void saveThumbnail(MultipartFile thumbnail) throws IOException {
        if(thumbnail.getSize() != 0){
            // local
//            String baseDir = "D:\\study\\mytoy\\thumbnail_image";
//            String filePath = baseDir + "\\" + thumbnail.getOriginalFilename();
            // aws 서버
            String baseDir = "/home/ec2-user/bookstore/thumbnail/";
            String filePath = baseDir + thumbnail.getOriginalFilename();
            thumbnail.transferTo(new File(filePath));
            this.thumbnailType = thumbnail.getOriginalFilename();
            this.thumbnailPath = filePath;
        }else{
            this.thumbnailType = "noImage.jpg"; // 이미지가 없을 경우 DB는 'NULL' 으로 처리.
            this.thumbnailPath = null; // 이미지가 없을 경우 DB는 'NULL' 으로 처리.
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
        this.author = bookDto.getAuthor();
        this.publisher = bookDto.getPublisher();
        this.publishedDate = LocalDate.parse(bookDto.getPublishedDate());
        this.price = bookDto.getPrice();
        this.disRate = bookDto.getDisRate();
        this.disPrice = bookDto.getDisPrice();
        this.shippingFee = bookDto.getShippingFee();
        if(bookDto.getThumbnail().getSize() != 0 || bookDto.getThumbnailType().equals("noImage.jpg")){
            this.saveThumbnail(bookDto.getThumbnail());
        }else{
            this.thumbnailType = bookDto.getThumbnailType();
            this.thumbnailPath = bookDto.getThumbnailPath();
        }
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
