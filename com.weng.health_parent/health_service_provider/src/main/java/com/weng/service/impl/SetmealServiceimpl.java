package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weng.constant.RedisConstant;
import com.weng.dao.CheckGroupDao;
import com.weng.dao.SetmealDao;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.CheckGroup;
import com.weng.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;
import weng.service.CheckGroupService;
import weng.service.SetmealService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceimpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        String fileName = setmeal.getImg();
        this.setmealAndCheckGroup(setmealId,checkgroupIds);
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
       long checkGroupCount = setmealDao.findCheckGroupIdBysetMeal(id);
        if (checkGroupCount > 0){
            throw new RuntimeException("存在下级检查组，不允许删除");
        }else {
            setmealDao.deleteById(id);
        }
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.edit(setmeal);
        setmealDao.deleteAllcheckgroupId(setmeal.getId());
        Integer setmealId = setmeal.getId();
        if (checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setmealAndCheckGroup(map);
            }
        }

    }


    public void setmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds){
        if (checkgroupIds != null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setmealAndCheckGroup(map);
            }
        }
    }
}

