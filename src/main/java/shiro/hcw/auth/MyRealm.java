package shiro.hcw.auth;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import shiro.hcw.pojo.User;
import shiro.hcw.restful.RestData;
import shiro.hcw.service.UserService;
import shiro.hcw.utils.MD5Util;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/11/29 11:17
 * Description:
 * Others:
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user_info");

        if (user.getUserName().equals("admin")) {
            info.addStringPermission("*");
        }
        if (user.getUserName().equals("username0")) {
            info.addRole("0");
        } else info.addRole("default");
        return info;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername(); //得到用户名
        String password = new String(usernamePasswordToken.getPassword());// 得到密码
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        User user = userService.login(username,MD5Util.MD5(password));

        if (user == null) {
            throw new RuntimeException("用户名或密码不正确");
        }
        SecurityUtils.getSubject().getSession().setAttribute("user_info", user);

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
