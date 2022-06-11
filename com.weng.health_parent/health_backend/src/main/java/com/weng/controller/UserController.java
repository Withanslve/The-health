package com.weng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weng.constant.MessageConstant;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weng.service.UserService;

import javax.ws.rs.core.SecurityContext;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/getUsername")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null){
            String username = user.getUsername();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,username);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @Reference
    private UserService userService;
    @RequestMapping("editPassword")
    public Result editPassword(@RequestBody com.weng.pojo.User user){
        User now = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nowUsername = now.getUsername();
        try {
            user.setUsername(nowUsername);
            userService.editPassword(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_USER_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.pageQuery(queryPageBean);
        return pageResult;
    }

}
