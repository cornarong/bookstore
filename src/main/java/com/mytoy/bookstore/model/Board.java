package com.mytoy.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytoy.bookstore.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate regdate;
    private int views;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    /**
     * 비즈니스 로직
     * 객체 지향에 가깝게 직접 엔티티에 설계함으로써 관리하기에도 편한다.
     */

    /* 게시글 수정 메소드 */
    public Board update(BoardDto boardDto){
        this.setTitle(boardDto.getTitle());
        this.setContent(boardDto.getContent());
        return this;
    }

    /* 게시글 삭제 메소드 */
    public Board delete(BoardDto boardDto){
        this.setTitle(boardDto.getTitle());
        this.setContent(boardDto.getContent());
        return this;
    }

}
