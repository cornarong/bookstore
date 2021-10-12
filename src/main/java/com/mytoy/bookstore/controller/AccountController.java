package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.form.UserForm;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

/*    @PostMapping("/login")
    public String success(){
        return "redirect:/";
    }*/

    @GetMapping("/register")
    public String register(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute(userForm);
        return "account/register";
    }

    @PostMapping("/register")
    public String register(UserForm userForm){
        userService.save(userForm);

        return "redirect:/";
    }

}
