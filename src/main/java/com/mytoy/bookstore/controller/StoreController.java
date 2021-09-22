package com.mytoy.bookstore.controller;

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

    // 아이템 리스트(관리자)
    @GetMapping("/manageList")
    public String manageItems(){
        return "store/manage/manageList";
    }

    // 아이템 등록 화면(관리자)
    @GetMapping("/addForm")
    public String addForm(Model model){
        return "store/manage/addForm";
    }

    // 아이템 상세페이지 화면(관리자)
    @GetMapping("/manageDetail")
    public String manageDetail(Model model){
        return "store/manage/manageDetail";
    }


}
