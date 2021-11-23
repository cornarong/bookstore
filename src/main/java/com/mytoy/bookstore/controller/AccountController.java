package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.UserDto;
import com.mytoy.bookstore.repository.UserRepository;
import com.mytoy.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
        if (trustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
            return "account/login";
        }
        return "redirect:/";
    }

    /* 회원가입 페이지 */
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDto", new UserDto());

        return "account/register";
    }
    /* 회원가입 */
    @PostMapping("/register")
    public String register(@Valid UserDto userDto, BindingResult bindingResult) throws IOException {
        if(userRepository.findByUid(userDto.getUid()) != null){ // 서버에서 아이디 중복 한번 더 확인.
            FieldError fieldError = new FieldError("userDto","uid","아이디를 다시 작성해주세요");
            bindingResult.addError(fieldError);
        }
        if(bindingResult.hasErrors()){
            return "account/register";
        }
        userService.save(userDto);
        return "redirect:/";
    }
}
