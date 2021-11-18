package com.mytoy.bookstore.controller.api;

import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import com.mytoy.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class UserApiController {

    private final UserRepository userRepository;
    private final UserService userService;

    // 사용자 조회
//    @GetMapping("/users")
//    Iterable<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
//        Iterable<User> users = null;
//        // method가 안오면 선택대상 자체가 null이라 조건을 주려면 뒤집어야 한다.
//        if("query".equals(method)){
//            users = userRepository.findByUidQuery(text);
//        }else if("nativeQuery".equals(method)){
//            users = userRepository.findByUidNativeQuery(text);
//        }else if("querydsl".equals(method)){
//            QUser user = QUser.user;
//            Predicate predicate = user.uid.contains(text);
//            users = userRepository.findAll(predicate);
//        }else{
//            users = userRepository.findAll();
//        }
//        return users;
//    }

    @PostMapping("/users}")
    User user(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{userId}")
    User oneUser(@PathVariable Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

/*    @PutMapping("/users/{userId}")
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
    }*/

    /* 회원 삭제  */
    @Secured("ROLE_ADMIN") // 해당 권한만 접근 가능
    @DeleteMapping("/user/{userId}")
    void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }
}