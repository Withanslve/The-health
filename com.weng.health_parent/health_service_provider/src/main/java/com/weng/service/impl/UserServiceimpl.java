package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weng.dao.PermissionDao;
import com.weng.dao.RoleDao;
import com.weng.dao.UserDao;
import com.weng.entity.PageResult;
import com.weng.entity.QueryPageBean;
import com.weng.pojo.Member;
import com.weng.pojo.Permission;
import com.weng.pojo.Role;
import com.weng.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import weng.service.UserService;

import java.util.List;
import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceimpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null){
            return null;
        }
        Integer id = user.getId();

        Set<Role> roles = roleDao.findByUserId(id);
        for (Role role : roles) {
            Integer roleId = role.getId();
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    public void editPassword(User user) {
        String password = user.getPassword();
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashpw);
        userDao.editPassword(user);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        //查询条件
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<User> page = userDao.selectByCondition(queryString);
        long total = page.getTotal(); //总记录数
        List<User> rows = page.getResult();
        return new PageResult(total,rows);
    }
}
