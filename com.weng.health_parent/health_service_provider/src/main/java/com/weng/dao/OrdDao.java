package com.weng.dao;


import com.github.pagehelper.Page;
import com.weng.pojo.Ord;

import java.util.Map;

public interface OrdDao {
    void setUserIdAndOrdid(Map<String, Integer> map);

    void add(Ord ord);

    Page<Ord> selectByCondition(String queryString);

    void deleteById(Integer id);
}
