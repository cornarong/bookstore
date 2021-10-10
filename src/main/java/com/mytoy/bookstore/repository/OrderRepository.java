package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
