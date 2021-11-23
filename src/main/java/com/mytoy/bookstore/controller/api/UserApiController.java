package com.mytoy.bookstore.controller.api;

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

    /* 회원 삭제  */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/user/{userId}")
    void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }

    /* 아이디 중복 확인 (0 - 사용가능, 1 - 아이디중복) */
    @ResponseBody
    @GetMapping("/uidDuplicateCheck/{uid}")
    public String uidDuplicateCheck(@PathVariable String uid){
        if(userRepository.findByUid(uid) != null){
            return "1";
        }
        return "0";
    }
}