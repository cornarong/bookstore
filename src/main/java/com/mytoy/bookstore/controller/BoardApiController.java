package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;


@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository boardRepository;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return boardRepository.findAll();
        }else{
            return boardRepository.findByTitleOrContent(title, content);
        }
    }
    // end::get-aggregate-root[]

    @PostMapping("/boards")
    Board board(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    // Single item

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

    @DeleteMapping("/boards/{boardId}")
    void deleteBoard(@PathVariable Long boardId) {
        boardRepository.deleteById(boardId);
    }
}