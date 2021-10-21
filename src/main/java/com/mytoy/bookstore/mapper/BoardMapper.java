package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.User;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper
public interface BoardMapper {

    /* BoardDto -> BoardEntity */
    default Board toBoardEntity(BoardDto boardDto, User user){
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .type("nomal")
                .regdate(LocalDate.now())
                .views(1)
                .user(user)
                .build();
        return board;
    }

    /* BoardEntity -> BoardDto */
    default BoardDto toBoardDto(Board board){
        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .type("nomal")
                .writer(board.getUser().getUid())
                .regdate(board.getRegdate())
                .views(board.getViews() + 1)
                .build();
        return boardDto;
    }
}
