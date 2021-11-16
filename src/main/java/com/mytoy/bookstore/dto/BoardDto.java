package com.mytoy.bookstore.dto;

import com.mytoy.bookstore.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Size(min=2, message = "내용은 최소 한글자 이상입니다")
    private String content;
    private String writer;
    private LocalDateTime regdate;
    private int views;

    /* 게시글 등록인 저장 */
    public void saveWriter(String userUid){
        this.writer = userUid;
    }

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public Page<BoardDto> toDtoList(Page<Board> boardList){
        Page<BoardDto> boardDtoList = boardList.map(m -> BoardDto.builder()
                .id(m.getId())
                .type(m.getType())
                .title(m.getTitle())
                .content(m.getContent())
                .writer(m.getUser().getUid())
                .regdate(m.getRegdate())
                .views(m.getViews())
                .build());
        return boardDtoList;
    }
}
