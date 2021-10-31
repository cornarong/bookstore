package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long id);

}
