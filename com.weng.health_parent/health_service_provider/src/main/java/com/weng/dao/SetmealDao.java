package com.weng.dao;

import com.github.pagehelper.Page;
import com.weng.pojo.CheckGroup;
import com.weng.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);
    void setmealAndCheckGroup(Map<String, Integer> map);

    Page<CheckGroup> findByCondition(String queryString);

    long findCheckGroupIdBysetMeal(Integer id);

    void deleteById(Integer id);

    Setmeal findById(Integer id);

    void edit(Setmeal setmeal);

    void deleteAllcheckgroupId(Integer id);
}
