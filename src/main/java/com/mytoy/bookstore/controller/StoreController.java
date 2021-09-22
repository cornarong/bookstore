package com.mytoy.bookstore.controller;

import com.mytoy.bookstore.model.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/store")
public class StoreController {

    @GetMapping
    public String store(){
        return "store/list";
    }

    @GetMapping("/manage")
    public String manageItems(){
        return "store/manage";
    }
    // 아이템 등록 화면
    @GetMapping("/add")
    public String addForm(Model model){

        return "store/addForm";
    }


}
