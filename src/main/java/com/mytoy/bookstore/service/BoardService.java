package com.mytoy.bookstore.service;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.mapper.BoardMapper;
import com.mytoy.bookstore.mapper.impl.BoardMapperImpl;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BoardRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /* 게시글 전체 목록 가져오기 */
    public Page<BoardDto> all(Pageable pageable, String searchTerm) {
        Page<Board> boardList = boardRepository.findByTitleContainingOrContentContainingOrderByRegdateDesc(searchTerm, searchTerm, pageable);
        Page<BoardDto> boardDtoList = new BoardDto().toDtoList(boardList); // Page<Entity> -> Page<Dto> 변환.

        return boardDtoList;
    }

    /* 게시글 저장 하기 */
    @Transactional(readOnly = false)
    public Board save(BoardDto boardDto, String uid){

        BoardMapper boardMapper = new BoardMapperImpl();
        Board board = boardMapper.toBoardEntity(boardDto);

        User user = userRepository.findByUid(uid);
        board.saveUser(user);
        board.saveRegDate();
        board.saveType();

        return boardRepository.save(board);
    }

    /* 게시글 단건 가져오기 */
    @Transactional(readOnly = false)
    public BoardDto detail(Long boardId, String from){
        Board board = boardRepository.findById(boardId).orElse(null);

        BoardMapper boardMapper = new BoardMapperImpl();
        BoardDto boardDto = boardMapper.toBoardDto(board);

        if(from.equals("detail")) board.addViews();
        boardDto.saveWriter(board.getUser().getUid());

        return boardDto;
    }

    /* 게시글 수정 하기 */
    @Transactional
    public Board edit(BoardDto boardDto, Long boardId){
        Board board = boardRepository.findById(boardId).orElse(null);
        board.edit(boardDto);
        return board;
    }

    /* 게시글 삭제 하기 */
    @Transactional
    public void delete(Long boardId){
        boardRepository.deleteById(boardId);
    }
}
