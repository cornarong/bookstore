package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleOrContent(String title, String content);

    // Containing : like와 비슷한 문법
    Page<Board> findByTitleContainingOrContentContainingOrderByRegdateDesc(String title, String content, Pageable pageable);

}
