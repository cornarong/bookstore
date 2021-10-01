package com.mytoy.bookstore.service;

import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BoardRepository;
import com.mytoy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    // 게시글 저장
    public Board save(Board board, String uid){
        User user = userRepository.findByUid(uid);
        board.setUser(user);
        return boardRepository.save(board);
    }

    // 게시글 수정
    public Board edit(Board board, String uid){
        User user = userRepository.findByUid(uid);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
