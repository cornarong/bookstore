package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.dto.UserDto;
import com.mytoy.bookstore.repository.UserRepository;
import com.mytoy.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /* 내정보 페이지 */
    @GetMapping
    public String info(Model model, Authentication authentication){
        String uid = authentication.getName();
        UserDto userDto = userService.detail(userRepository.findByUid(uid).getId());

        model.addAttribute("userDto",userDto);
        return "user/infoForm";
    }

    /* 내정보 수정 페이지 */
    @GetMapping("/edit")
    public String editInfo(Model model, Authentication authentication){
        String uid = authentication.getName();
        UserDto userDto = userService.detail(userRepository.findByUid(uid).getId());

        model.addAttribute("userDto",userDto);
        return "user/infoEditForm";
    }

    /* 내정보 수정 */
    @PutMapping("/edit/{userId}")
    public String edit(@PathVariable Long userId, @Valid UserDto userDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) throws IOException {
        if(bindingResult.hasErrors()){
            return "user/infoEditForm";
        }
        userService.edit(userId, userDto);

        redirectAttributes.addAttribute("edit", true);
        return "redirect:/info";
    }
}
