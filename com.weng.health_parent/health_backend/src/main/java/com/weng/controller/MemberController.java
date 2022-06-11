package com.weng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weng.constant.MessageConstant;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.entity.Result;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Member;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weng.service.CheckItemService;
import weng.service.MemberService;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

/*
*检查项管理
*/
@RestController
@RequestMapping("/member")
public class MemberController {
    @Reference//查找服务
    private MemberService memberService;

    @PreAuthorize("hasAuthority('USER_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody Member member){
        try {
            memberService.add(member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MEMBER_FAIL);
        }
        return new Result(true,MessageConstant.ADD_MEMBER_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = memberService.pageQuery(queryPageBean);
        return pageResult;
    }

    @PreAuthorize("hasAuthority('USER_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody Member member){
        try {
            memberService.edit(member);
            return new Result(true,MessageConstant.EDIT_MEMBER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_MEMBER_SUCCESS);
        }
    }


    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Member member= memberService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,member);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Member> list = memberService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try{
            memberService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.DELETE_MEMBER_FAIL);
        }
        return  new Result(true, MessageConstant.DELETE_MEMBER_SUCCESS);
    }

    @RequestMapping("number")
    public Result getMemberNumber(){
        Map<String, Object> map = new HashMap<>();
        List<String> months = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);

        for (int i = 0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy-MM").format(date));
        }
        map.put("months",months);

        List<Integer> memberCount = memberService.findMemberCountByMonths(months);
        map.put("memberCount",memberCount);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,map);



    }
}
