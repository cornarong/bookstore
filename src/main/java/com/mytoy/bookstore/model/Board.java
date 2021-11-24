package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    private String type;
    private LocalDateTime regdate;
    private int views;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 비즈니스 로직
     */

    /* 게시글 등록일 저장 */
    public void saveRegDate(){
        this.regdate = LocalDateTime.now();
    }

    /* 게시글 조회수 초기값 저장 */
    public void addViews(){
        this.views += 1;
    }

    /* 게시글 등록인 저장 */
    public void saveUser(User user){
        this.user = user;
    }

    /* 게시글 타입 저장 */
    public void saveType(){
        this.type = "nomal";
    }

    /* 게시글 수정 메소드 */
    public void edit(BoardDto boardDto){
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }

    /* 게시글 삭제 메소드 */
    public void delete(BoardDto boardDto){
    }

}
