package com.weng.dao;


import com.github.pagehelper.Page;
import com.weng.pojo.Ord;
import com.weng.pojo.OrderSetting;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    void editNumberByOrderDate(OrderSetting orderSetting);
    long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    Page<Ord> selectByCondition(String queryString);

    void updateNumber(Date ordTime);
}
