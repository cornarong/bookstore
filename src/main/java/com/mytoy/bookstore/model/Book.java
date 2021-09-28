package com.mytoy.bookstore.model;

import com.mytoy.bookstore.form.BookForm;
import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;

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
    private String thumbnail; // 섬네일
    private String thumbnailPath; // 섬네일 물리 경로

    public Book createBook(BookForm bookForm) throws IOException {

        Book book = new Book();

        book.title = bookForm.getTitle();
        book.subTitle = bookForm.getSubTitle();
        book.content = bookForm.getContent();
        book.author = bookForm.getAuthor();
        book.publisher = bookForm.getPublisher();
        book.publishedDate = (bookForm.getPublishedDate().equals("") ? null : bookForm.getPublishedDate());
        book.price = bookForm.getPrice();
        book.disRate = bookForm.getDisRate();
        book.disPrice = bookForm.getDisPrice();
        book.quantity = bookForm.getQuantity();
        book.shippingFee = bookForm.getShippingFee();

        /* 파일 업로드 처리*/
        String baseDir = "D:\\study\\files"; // 저장파일 물리경로
        String filePath = baseDir + "\\" + bookForm.getThumbnail().getOriginalFilename(); // 저장파일명
        bookForm.getThumbnail().transferTo(new File(filePath));
        book.thumbnail = bookForm.getThumbnail().getOriginalFilename();
        book.thumbnailPath = filePath;

        return book;
    }
}
