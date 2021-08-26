package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.service.BoardService;
import com.mytoy.bookstore.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private BoardService boardService;

    // 게시글 목록
    @GetMapping
    public String list(Model model, @PageableDefault(size = 4) Pageable pageable,
                       @RequestParam(defaultValue = "") String searchTerm){
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm, pageable);
        int startPage = Math.max(1,boards.getPageable().getPageNumber() - 10);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 10);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    // 게시글 상세페이지
    @GetMapping("/{boardId}")
    public String from(@PathVariable Long boardId, Model model){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return "redirect:/board";
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
    @PostMapping("/add/{boardId}")
    public String add(@Valid Board board, BindingResult bindingResult,
                      RedirectAttributes riRedirectAttributes, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/addForm";
        }
        String username = authentication.getName();
        Board savedBoard = boardService.save(board, username);
        riRedirectAttributes.addAttribute("savedBoardId",savedBoard.getId());
        riRedirectAttributes.addAttribute("saved",true);
        return "redirect:/board/{savedBoardId}";
    }

    // 게시글 수정화면
    @GetMapping("/edit/{boardId}")
    public String editForm(@PathVariable Long boardId, Model model){
        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return "redirect:/board";
        }
        model.addAttribute("board",board);
        return "board/editForm";
    }

    // 게시글 수정
    @PutMapping("/edit/{boardId}") // HiddenHttpMethodFilter 사용
    public String edit(@Valid Board board, BindingResult bindingResult,
                       @PathVariable Long boardId, RedirectAttributes redirectAttributes, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/editForm";
        }
        String username = authentication.getName();
        Board editedBoard = boardService.edit(board, username);
        redirectAttributes.addAttribute("editedBoardId",editedBoard.getId());
        redirectAttributes.addAttribute("edited",true);
        return "redirect:/board/{editedBoardId}";
    }

    // 게시글 삭제
    @Secured("ROLE_USER")
    @ResponseBody
    @DeleteMapping("/delete/{boardId}") // ajax 전송
    void delete(@PathVariable Long boardId){
        boardRepository.deleteById(boardId);
    }
}
