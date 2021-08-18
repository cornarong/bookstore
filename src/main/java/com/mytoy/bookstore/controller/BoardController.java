package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    // 게시글 목록
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 4) Pageable pageable, @RequestParam(defaultValue = "") String searchTerm){
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm, pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
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
    public String addForm(Model model){
        model.addAttribute("board", new Board());
        return "board/addForm";
    }

    // 신규 게시글 저장
    @PostMapping("/add")
    public String add(@Valid Board board, BindingResult bindingResult, RedirectAttributes riRedirectAttributes){
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/addForm";
        }
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
    public String edit(@Valid Board board, BindingResult bindingResult, @PathVariable Long boardId, RedirectAttributes redirectAttributes){
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/editForm";
        }
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
