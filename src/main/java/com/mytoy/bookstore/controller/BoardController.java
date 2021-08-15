package com.mytoy.bookstore.controller;

import lombok.extern.slf4j.Slf4j;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "board/list";
    }

    @GetMapping("/list/{boardId}")
    public String from(@PathVariable Long boardId, Model model){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return "redirect:/board/list";
        }
        model.addAttribute("board",board);
        return "board/post";
    }

    @PostMapping("/form")
    public String form(@ModelAttribute Board board){
        boardRepository.save(board);
        return "redirect:/board/list";
    }

    // 신규 게시글 작성화면 이동
    @GetMapping("/add")
    public String addForm(){
        return "board/addForm";
    }

    // 신규 게시글 저장
    @PostMapping("/add")
    public String add(){
        return "";
    }

    // 게시글 수정화면 이동.
    @GetMapping("/edit/{boardId}")
    public String editForm(@PathVariable Long boardId, Model model){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return "redirect:/board/list";
        }
        model.addAttribute("board",board);
        return "board/editForm";
    }

}
