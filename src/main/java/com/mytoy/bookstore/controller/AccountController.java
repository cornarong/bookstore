package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.form.UserForm;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute(userForm);
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserForm userForm, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.info("에러 = {}", bindingResult.getFieldError());
            return "account/register";
        }
        userService.save(userForm);
        return "redirect:/";
    }
}
