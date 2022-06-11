package com.weng.dao;

import com.github.pagehelper.Page;
import com.weng.pojo.CheckItem;
import com.weng.pojo.Material;

public interface MaterialDao {
    void add(Material material);

    Page<Material> selectByCondition(String queryString);

    void deleteById(Integer id);

    void edit(Material material);

    Material findById(Integer id);
}
