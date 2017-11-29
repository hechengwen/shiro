package shiro.hcw.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import shiro.hcw.pojo.User;
import shiro.hcw.restful.RestData;
import shiro.hcw.service.UserService;
import shiro.hcw.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/11/29 13:16
 * Description:
 * Others:
 */
@Controller
public class LoginController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    /**
     * 登陆页
     *
     * @param request
     * @return
     * @history
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception{

        ModelAndView model = new ModelAndView("index");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("用户名或密码不能为空");
        }
        User user = userService.login(username, MD5Util.MD5(password));

        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        SecurityUtils.getSubject().login(token);

        model.addObject("data",user);
        return model;
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public String logout(){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        logger.info("安全退出！！！");
        return "redirect:/login";
    }

    @RequestMapping("batchInsert")
    public void batchInsert() {
        User user = new User();
        for (int i = 0; i <= 200; i++) {
            user.setUserName("username" + i);
            user.setPassword(MD5Util.MD5("password" + i));
            user.setMobile("1771036389" + i);
            if (i % 2 == 0) {
                user.setRealName("zhangsan" + i);
            } else user.setRealName("lisi" + i);
            userService.insert(user);
        }
    }

    @RequestMapping("testRole")
    @ResponseBody
    public void testRole(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user_info");

        User user1 = (User) SecurityUtils.getSubject().getSession().getAttribute("user_info");

        logger.info("test-----role-------------------user:"+user);
        logger.info("test-----role-------------------user1:"+user1);
    }

}
