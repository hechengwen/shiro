package shiro.hcw.service;

import shiro.hcw.pojo.User;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/11/29 13:36
 * Description:
 * Others:
 */
public interface UserService {
    /**
     * 插入数据
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 登录查询
     * @param username
     * @param password
     * @return
     */
    User login(String username,String password);
}
