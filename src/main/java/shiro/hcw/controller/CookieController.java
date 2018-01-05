package shiro.hcw.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/20 13:18
 * Description:
 * Others:
 */
@Controller
@Slf4j
public class CookieController {

    @RequestMapping("getCookie")
    public void getCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");

        //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String curTime = format.format(new Date());
        String lastTime = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("lastTime")) {
                    lastTime = c.getValue();
                    response.getWriter().write("欢迎回来，你上次访问的时间为："+lastTime+",当前时间为："+curTime);
                    c.setValue(curTime);
                    c.setMaxAge(30*24*60*60);
                    response.addCookie(c);
                    break;
                }
            }
        }

        if (cookies == null || lastTime == null) {
            response.getWriter().write("你是首次访问本网站，当前时间为："+curTime);
            Cookie cookie = new Cookie("lastTime",curTime);
            cookie.setMaxAge(30*24*60*60);
            response.addCookie(cookie);
        }

    }

}
