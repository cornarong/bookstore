package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.model.Board;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;


@RestController
@RequestMapping("/api")
class UserApiController {

    @Autowired
    private UserRepository userRepository;

    // 사용자 조회
    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @PostMapping("/users}")
    User user(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{userId}")
    User one(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/users/{userId}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
                    user.setBoards(newUser.getBoards());
//                    user.getBoards().clear(); // 해당 유저의 게시물 전부 삭제하고
//                    user.getBoards().addAll(newUser.getBoards()); // 새로 JSON으로 들어온 데이터만 넣어진다.
                    for(Board board : user.getBoards()){ // 해당 유저의 모든 게시글의 유저 정보 데이터를 변경
                        board.setUser(user);
                    }
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(userId);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable Long userId) {
        userRepository.deleteById(userId);
    }
}