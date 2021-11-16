package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.dto.BoardDto.BoardDtoBuilder;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.Board.BoardBuilder;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-15T20:02:29+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board toBoardEntity(BoardDto boardDto) {
        if ( boardDto == null ) {
            return null;
        }

        BoardBuilder board = Board.builder();

        board.id( boardDto.getId() );
        board.title( boardDto.getTitle() );
        board.content( boardDto.getContent() );
        board.type( boardDto.getType() );
        board.regdate( boardDto.getRegdate() );
        board.views( boardDto.getViews() );

        return board.build();
    }

    @Override
    public BoardDto toBoardDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDtoBuilder boardDto = BoardDto.builder();

        if ( board.getId() != null ) {
            boardDto.id( board.getId() );
        }
        boardDto.type( board.getType() );
        boardDto.title( board.getTitle() );
        boardDto.content( board.getContent() );
        boardDto.regdate( board.getRegdate() );
        boardDto.views( board.getViews() );

        return boardDto.build();
    }
}
