package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weng.dao.OrdDao;
import com.weng.dao.OrderSettingDao;
import com.weng.dao.SetmealDao;
import com.weng.dao.UserDao;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.Ord;
import com.weng.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import weng.service.OrdService;

import java.util.*;

@Service(interfaceClass = OrdService.class)
@Transactional
public class OrdServiceimpl implements OrdService {
    @Autowired
    private OrdDao ordDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(Ord ord,Integer id,String username) {
        User user = userDao.findByUsername(username);
        float balance = user.getBalance();
        Float price = setmealDao.findById(id).getPrice();
        if (balance > price){
            //uuid
            ord.setOrdCode(UUID.randomUUID().toString());
            //creatime
            ord.setCreateTime(new Date());
            //userid
            Integer userId = user.getId();
            //t_ord_user
            Map<String,Integer> map = new HashMap<>();
            map.put("userId",userId);
            map.put("ordId",ord.getId());

            ordDao.setUserIdAndOrdid(map);
            ord.setUserId(userId);
            ord.setSetmealCode(setmealDao.findById(id).getCode());

            user.setBalance(balance-price);
            userDao.updateBalance(user);

            Date ordTime = ord.getOrdTime();
            orderSettingDao.updateNumber(ordTime);
            ordDao.add(ord);
        }else {
            throw new RuntimeException("余额不足");
        }

    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //查询条件
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Ord> page = orderSettingDao.selectByCondition(queryString);
        long total = page.getTotal(); //总记录数
        List<Ord> rows = page.getResult();
        return new PageResult(total,rows);
    }

    @Override
    public void deleteById(Integer id) {
        ordDao.deleteById(id);
    }
}
