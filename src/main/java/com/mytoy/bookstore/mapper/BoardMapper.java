package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.User;
import org.mapstruct.Mapper;

import java.time.LocalDate;

@Mapper
public interface BoardMapper {

    Board toBoardEntity(BoardDto boardDto);

    BoardDto toBoardDto(Board board);
}
