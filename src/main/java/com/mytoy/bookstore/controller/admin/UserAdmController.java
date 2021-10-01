package com.mytoy.bookstore.controller.admin;


import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user")
public class UserAdmController {

    private final UserRepository userRepository;

    // 회원 목록(관리자)
    @GetMapping
    public String list(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "/admin/user/list";
    }

}
