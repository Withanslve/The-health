package com.weng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weng.constant.MessageConstant;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.entity.Result;
import com.weng.pojo.CheckGroup;
import com.weng.pojo.Ord;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weng.service.OrdService;

/**
 * 检查组管理
 */
@RestController
@RequestMapping("/ord")
public class OrdController {
    @Reference
    private OrdService ordService;
    @RequestMapping("/add")
    public Result add(@RequestBody Ord ord,Integer id){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        try {
            ordService.add(ord,id,username);
            return new Result(true,MessageConstant.ADD_MEMBER_SUCCESS);
        } catch (Exception e) {
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = ordService.pageQuery(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            ordService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, e.getMessage());
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

}
