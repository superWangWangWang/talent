package com.jiantai.talent.interceptor;



import com.jiantai.talent.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 拦截器
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        //登录页面和登录接口的url不拦截  ///talent/public/login

        if (path.indexOf("public") != -1){//访问的是公共路径，允许
            return true;
        }else {
            //查询session是否有user对象
            User user = (User)request.getSession().getAttribute("user");
            if (user == null){//没有登录，不允许访问
                response.sendRedirect(request.getContextPath()+"/public/login");
                return false;
            }else {
                //需要判断是不是 用户/公司 user.type
               return true;
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle==========================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("afterCompletion==========================");
    }
}
