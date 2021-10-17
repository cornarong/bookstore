package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.form.UserDto;
import com.mytoy.bookstore.repository.UserRepository;
import com.mytoy.bookstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "account/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute(userDto);
        return "account/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto, BindingResult bindingResult) throws IOException {
        if(userRepository.findByUid(userDto.getUid()) != null){ // 서버에서 아이디 중복 한번 더 확인.
            FieldError fieldError = new FieldError("userDto","uid","아이디를 다시 작성해주세요");
            bindingResult.addError(fieldError);
        }
        if(bindingResult.hasErrors()){
            log.info("에러 = {}", bindingResult.getFieldError());
            return "account/register";
        }
        userService.save(userDto);
        return "redirect:/";
    }

    /**
     * 아이디 중복 확인
     * @param uid
     * @return checkValue (0 - 사용가능, 1 - 아이디중복)
     */
    @ResponseBody
    @GetMapping("/uidDuplicateCheck/{uid}")
    public String uidDuplicateCheck(@PathVariable String uid){
        String checkValue = "0";
        if(userRepository.findByUid(uid) != null){
            checkValue = "1";
        }
        return checkValue;
    }
}
