package com.mytoy.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    private long id;
    private String type;
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자 이상 30자 이하입니다")
    private String title;
    @NotNull
    @Size(min=2, message = "내용을 입력해주세요")
    private String content;
    private String writer;
    private LocalDate regdate;
    private int views;

}
