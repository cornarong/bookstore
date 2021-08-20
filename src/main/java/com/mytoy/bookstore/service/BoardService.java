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

    public Board save(Board board, String username){
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }
}
