package com.mytoy.bookstore.controller.admin;

import com.mytoy.bookstore.dto.UserInfoDto;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.UserRepository;
import com.mytoy.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    /* 회원 전체목록 */
    @GetMapping("/user")
    public String list(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        return "/admin/user/list";
    }

    /* 회원정보 상세페이지 */
    @GetMapping("/user/{userId}")
    public String detail(@PathVariable Long userId, Model model){
        UserInfoDto userInfoDto = userService.detail(userId);
        model.addAttribute("userInfoDto", userInfoDto);
        return "/admin/user/detailForm";
    }

    /* 회원정보 수정페이지 */
    @PostMapping("/update/{userId}")
    public String updateForm(@PathVariable Long userId, @ModelAttribute UserInfoDto userInfoDto, Model model){
        userInfoDto.setId(userId);
        model.addAttribute("userInfoDto", userInfoDto);
        return "admin/user/updateForm";
    }

    /* 회원정보 수정하기 */
    @PutMapping("/update/{userId}")
    public String Update(@PathVariable Long userId, @Valid UserInfoDto userInfoDto, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult.getFieldError());
            return "admin/user/updateForm";
        }
        userService.update(userId, userInfoDto);
        return "redirect:/admin/user";
    }
}
