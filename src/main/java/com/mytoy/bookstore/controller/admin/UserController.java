package com.mytoy.bookstore.controller.admin;


import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserController {

    private final UserRepository userRepository;

    // 회원 목록(관리자)
    @GetMapping
    public String list(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "/admin/user/list";
    }

    @GetMapping("/{userId}")
    public String detail(@PathVariable Long userId, Model model){
        User findUser = userRepository.findById(userId).orElse(null);
        model.addAttribute("user",findUser);
        return "/admin/user/detail";
    }

}