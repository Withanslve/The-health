package com.weng.dao;

import com.github.pagehelper.Page;
import com.weng.pojo.CheckGroup;
import com.weng.pojo.Setmeal;
import com.weng.pojo.User;

import java.util.ArrayList;
import java.util.Map;

public interface LoginDao {

    void add(User user);

    ArrayList<User> findByUsername(String username);
}
