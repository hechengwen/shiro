package shiro.hcw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shiro.hcw.dao.UserMapper;
import shiro.hcw.pojo.User;
import shiro.hcw.service.UserService;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/11/29 13:39
 * Description:
 * Others:
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username,password);
    }
}
