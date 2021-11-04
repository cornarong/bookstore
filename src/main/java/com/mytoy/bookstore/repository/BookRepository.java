package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.Basket;
import com.mytoy.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);

}
