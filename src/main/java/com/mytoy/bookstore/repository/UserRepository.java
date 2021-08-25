package com.mytoy.bookstore.repository;

import com.mytoy.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // EntityGraph 어노테이션 사용시 fetch타입 무시하고 board를 join으로 전부 조회한다.
    // User클래스의 board의 fetch = FetchType.LAZY or fetch = FetchType.EGAR 둘다 무시.
    // n+1 문제가 발생 시 여러개의 쿼리가 호출되지 않도록 할 때 사용된다. (성능향상)
    @EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    User findByUsername(String username);
}
