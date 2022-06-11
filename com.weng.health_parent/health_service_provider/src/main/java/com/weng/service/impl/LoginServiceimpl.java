package com.weng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.weng.dao.LoginDao;
import com.weng.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import weng.service.LoginService;

import java.util.ArrayList;

@Service(interfaceClass = LoginService.class)
@Transactional
public class LoginServiceimpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
    @Override
    public void add(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        ArrayList<User> list = loginDao.findByUsername(username);
        if (list.size() > 0){
            throw new RuntimeException("存在相同的用户");
        }else {
            user.setPassword(hashpw);
            loginDao.add(user);
        }
    }





}
