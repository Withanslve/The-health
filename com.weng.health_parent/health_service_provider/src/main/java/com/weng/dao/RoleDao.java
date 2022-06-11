package com.weng.dao;

import com.weng.pojo.Role;

import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(Integer userID);
}
