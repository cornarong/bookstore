package com.mytoy.bookstore.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/user/1")
    public String user(){
        return "user";
    }

    @GetMapping("/manager/1")
    public String manager(){
        return "manager";
    }

    @GetMapping("/admin/1")
    public String admin(){
        return "admin";
    }
}
