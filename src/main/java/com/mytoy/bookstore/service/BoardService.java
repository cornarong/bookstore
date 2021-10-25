package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.mapper.BoardMapper;
import com.mytoy.bookstore.mapper.BoardMapperImpl;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BoardRepository;
import com.mytoy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    /* 게시글 저장 */
    @Transactional(readOnly = false)
    public Board save(BoardDto boardDto, String uid){
        User user = userRepository.findByUid(uid);

        BoardMapper boardMapper = new BoardMapperImpl();
        Board board = boardMapper.toBoardEntity(boardDto, user);

        return boardRepository.save(board);
    }

    /* 게시글 상세정보 */
    @Transactional(readOnly = false)
    public BoardDto detail(Long boardId, String from){
        Board findBoard = boardRepository.findById(boardId).orElse(null);

        if(from.equals("detail")) findBoard.setViews(findBoard.getViews() + 1);
        else findBoard.setViews(findBoard.getViews());

        BoardMapper boardMapper = new BoardMapperImpl();
        BoardDto boardDto = boardMapper.toBoardDto(findBoard);
        return boardDto;
    }

    /* 게시글 수정 */
    @Transactional(readOnly = false)
    public Board update(BoardDto boardDto, Long boardId){
        Board board = boardRepository.findById(boardId).orElse(null);
        board.update(boardDto);
        return board;
    }

    /* 게시글 삭제 */
    @Transactional(readOnly = false)
    public void delete(Long boardId){
        boardRepository.deleteById(boardId);
    }
}
