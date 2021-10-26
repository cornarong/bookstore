package com.mytoy.bookstore.controller.admin;

import com.mytoy.bookstore.dto.UserDto;
import com.mytoy.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
/* 관리자 전용 회원관리 컨트롤러 입니다 */
public class MemberController {

    @Autowired
    private final UserService userService;

    /* 회원 목록 */
    @GetMapping("/user")
    public String list(Model model){
        List<UserDto> userDtoList = userService.all();

        model.addAttribute("userDtoList",userDtoList);
        return "/admin/user/list";
    }

    /* 회원정보 상세페이지 */
    @GetMapping("/user/{userId}")
    public String detail(@PathVariable Long userId, Model model){
        UserDto userDto = userService.detail(userId);

        model.addAttribute("userDto", userDto);
        return "/admin/user/detailForm";
    }

    /* 회원정보 수정페이지 */
    @GetMapping("/user/edit/{userId}")
    public String editForm(@PathVariable Long userId, Model model){
        UserDto userDto = userService.detail(userId);

        model.addAttribute("userDto", userDto);
        return "/admin/user/editForm";
    }

    /* 회원정보 수정 */
    @PutMapping("/user/edit/{userId}")
    public String edit(@PathVariable Long userId, @Valid UserDto userDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            log.info("error = {}", bindingResult.getFieldError());
            return "/admin/user/editForm";
        }
        userService.edit(userId, userDto);

        redirectAttributes.addAttribute("edit", true);
        return "redirect:/admin/user/" + userId;
    }
}
