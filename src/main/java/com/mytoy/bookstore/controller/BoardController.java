package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.BoardDto;
import com.mytoy.bookstore.service.BoardService;
import com.mytoy.bookstore.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.mytoy.bookstore.model.Board;
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
@RequiredArgsConstructor
public class BoardController {

    private final BoardValidator boardValidator; // 유효성 검사 커스텀
    private final BoardService boardService;

    /* 게시글 목록 */
    @GetMapping
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable, @RequestParam(defaultValue = "") String searchTerm){
        Page<BoardDto> boardDtoList = boardService.all(pageable, searchTerm);

        int startPage = Math.max(1, boardDtoList.getPageable().getPageNumber() - 10);
        int endPage = Math.min(boardDtoList.getTotalPages(), boardDtoList.getPageable().getPageNumber() + 10);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boardDtoList", boardDtoList);
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

        riRedirectAttributes.addAttribute("save",true);
        return "redirect:/board/" + board.getId();
    }

    /* 게시글 상세페이지 */
    @GetMapping("/{boardId}")
    public String detailForm(@PathVariable Long boardId, Model model, Authentication authentication){
        BoardDto boardDto = boardService.detail(boardId,"detail");

        if(authentication != null) { // 수정,삭제 가능 여부를 판단하기 위해 사용자의 uid 값을 인자로 가져간다.
            String uid = authentication.getName();
            model.addAttribute("auth_uid",uid);
        }

        model.addAttribute("boardDto",boardDto);
        return "board/detail";
    }

    /* 게시글 수정페이지 */
    @GetMapping("/edit/{boardId}")
    public String editForm(@PathVariable Long boardId, Model model){
        BoardDto boardDto = boardService.detail(boardId,"edit");

        model.addAttribute("boardDto",boardDto);
        return "board/editForm";
    }

    /* 게시글 수정 */
    @PutMapping("/edit/{boardId}")
    public String edit(@Valid BoardDto boardDto, BindingResult bindingResult,
                       @PathVariable Long boardId, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "board/editForm";
        }

        Board board = boardService.edit(boardDto, boardId);

        redirectAttributes.addAttribute("edit",true);
        return "redirect:/board/" + board.getId();
    }
}
