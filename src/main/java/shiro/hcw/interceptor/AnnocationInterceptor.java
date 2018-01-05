package shiro.hcw.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import shiro.hcw.anno.LoginRequired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/18 17:07
 * Description:
 * Others:
 */
public class AnnocationInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(o instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod myHandlerMethod = (HandlerMethod) o;
        Object bean = myHandlerMethod.getBean();
        Method method = myHandlerMethod.getMethod();
        LoginRequired classAnnotation = bean.getClass().getAnnotation(LoginRequired.class);//类上有该标记
        LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);//方法上有该标记
        if (loginRequired != null) {
            Object obj = httpServletRequest.getSession().getAttribute("login");
            if (null == obj) {
                String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/";
//                    httpServletResponse.sendRedirect(basePath);
                logger.info("basePath:{}", basePath);
            }
            String value = loginRequired.value();
            String s = classAnnotation.value();
            if ("login".equals(value)) {
                logger.info("登录成功！，value={}", value);
                return true;
            }
        }


        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
