package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.service.BoardService;
import com.mytoy.bookstore.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    /* 게시글 목록 */
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

    /* 게시글 등록 페이지 */
    @GetMapping("/add")
    public String addForm(Model model){
        BoardDto boardDto = new BoardDto();
        model.addAttribute("boardDto", boardDto);
        return "board/addForm";
    }

    /* 게시글 등록 */
    @PostMapping("/add")
    public String add(@Valid BoardDto boardDto, BindingResult bindingResult,
                      RedirectAttributes riRedirectAttributes, Authentication authentication){
//        boardValidator.validate(boardDto, bindingResult); // * 유효성 검사 커스텀
        if (bindingResult.hasErrors()) {
            return "board/addForm";
        }
        String uid = authentication.getName();
        Board board = boardService.save(boardDto, uid);
        riRedirectAttributes.addAttribute("saved",true);
        return "redirect:/board/"+board.getId();
    }

    /* 게시글 상세페이지 */
    @GetMapping("/{boardId}")
    public String detailForm(@PathVariable Long boardId, Model model, Authentication authentication){
        BoardDto boardDto = boardService.detail(boardId,"detail");
        String uid = authentication.getName();
        model.addAttribute("boardDto",boardDto);
        model.addAttribute("auth_uid",uid);
        return "/board/detailForm";
    }

    /* 게시글 수정페이지 */
    @GetMapping("/edit/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model){
        BoardDto boardDto = boardService.detail(boardId,"update");
        model.addAttribute("boardDto",boardDto);
        return "board/editForm";
    }

    /* 게시글 수정 */
    @PutMapping("/edit/{boardId}") // HiddenHttpMethodFilter 사용
    public String update(@Valid BoardDto boardDto, BindingResult bindingResult,
                       @PathVariable Long boardId, RedirectAttributes redirectAttributes, Authentication authentication){
        if (bindingResult.hasErrors()) {
            return "board/editForm";
        }
        Board board = boardService.update(boardDto, boardId);
        redirectAttributes.addAttribute("edited",true);
        return "redirect:/board/"+board.getId();
    }
}
