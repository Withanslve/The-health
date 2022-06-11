package com.weng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weng.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weng.service.LoginService;

@RestController
@RequestMapping("/register")
public class LoginController {
    @Reference
    private LoginService loginService;
    @RequestMapping("/add")
    public void add(User user){
        loginService.add(user);
    }


}
