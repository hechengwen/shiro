package shiro.hcw.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import shiro.hcw.pojo.User;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/11/29 11:20
 * Description:
 * Others:
 */
@Repository
public interface UserMapper {

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
    User login(@Param("username") String username, @Param("password") String password);

}
