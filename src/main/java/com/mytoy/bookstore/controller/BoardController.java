package com.mytoy.bookstore.controller;

import lombok.extern.slf4j.Slf4j;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 목록
    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "board/list";
    }

    // 게시글 상세페이지
    @GetMapping("/list/{boardId}")
    public String from(@PathVariable Long boardId, Model model){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return "redirect:/board/list";
        }
        model.addAttribute("board",board);
        return "board/post";
    }

    // 신규 게시글 작성화면
    @GetMapping("/add")
    public String addForm(){
        return "board/addForm";
    }

    // 신규 게시글 저장
    @PostMapping("/add")
    public String add(@ModelAttribute Board board, RedirectAttributes riRedirectAttributes){
        Board savedBoard = boardRepository.save(board);
        riRedirectAttributes.addAttribute("savedBoardId",savedBoard.getId());
        riRedirectAttributes.addAttribute("saved",true);
        return "redirect:/board/list/{savedBoardId}";
    }

    // 게시글 수정화면
    @GetMapping("/edit/{boardId}")
    public String editForm(@PathVariable Long boardId, Model model){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return "redirect:/board/list";
        }
        model.addAttribute("board",board);
        return "board/editForm";
    }

    // 게시글 수정
    @PostMapping("/edit/{boardId}")
    public String edit(@PathVariable Long boardId, @ModelAttribute Board board, RedirectAttributes redirectAttributes){
        board.setId(boardId);
        Board editedBoard = boardRepository.save(board);
        redirectAttributes.addAttribute("editedBoardId",editedBoard.getId());
        redirectAttributes.addAttribute("edited",true);
        return "redirect:/board/list/{editedBoardId}";
    }

    // 게시글 삭제
    @GetMapping("/delete/{boardId}")
    public String delete(@PathVariable Long boardId){
        boardRepository.deleteById(boardId);
        return "redirect:/board/list";
    }
}
