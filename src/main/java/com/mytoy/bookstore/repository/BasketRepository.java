package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    List<Basket> findAllByUserId(Long id);

    void deleteAllByUserId(Long userId);

}
