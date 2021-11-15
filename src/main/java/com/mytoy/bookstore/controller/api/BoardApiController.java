package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import com.mytoy.bookstore.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardApiController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    // 게시글 조회
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return boardRepository.findAll();
        }else{
            return boardRepository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards")
    Board board(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @GetMapping("/boards/{boardId}")
    Board one(@PathVariable Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{boardId}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long boardId) {
        return boardRepository.findById(boardId)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return boardRepository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(boardId);
                    return boardRepository.save(newBoard);
                });
    }

    /* 게시글 삭제 */
    @DeleteMapping("/board/{boardId}")
    void delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
    }
}