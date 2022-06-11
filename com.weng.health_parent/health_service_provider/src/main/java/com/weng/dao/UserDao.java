package com.weng.dao;

import com.github.pagehelper.Page;
import com.weng.pojo.User;

public interface UserDao {
    User findByUsername(String username);

    void editPassword(User user);

    Page<User> selectByCondition(String queryString);

    void updateBalance(User user);
}
