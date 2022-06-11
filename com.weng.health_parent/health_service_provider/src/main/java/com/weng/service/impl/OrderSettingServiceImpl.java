package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.weng.dao.OrderSettingDao;
import com.weng.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import weng.service.OrderSettingService;

import java.util.*;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    OrderSettingDao orderSettingDao;
    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
               long countByOrderDate =orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
               if (countByOrderDate > 0){
                   orderSettingDao.editNumberByOrderDate(orderSetting);
               }else {
                    orderSettingDao.add(orderSetting);
               }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String begin = date + "-1";
        String end = date + "-31";
        HashMap<String, String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                Map<String,Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }

        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if (count>0){
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else {
            orderSettingDao.add(orderSetting);
        }
    }
}
