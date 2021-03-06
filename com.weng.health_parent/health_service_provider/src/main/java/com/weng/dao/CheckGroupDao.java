package com.weng.dao;

import com.github.pagehelper.Page;
import com.weng.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map map);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer id);

    List<CheckGroup> findAll();


    long findCheckItemCountIdByGroup(Integer id);

    long findsetMealCountIdByGroup(Integer id);

    void deleteById(Integer id);


}
