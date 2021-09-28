package com.zrl.store.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/* 登录拦截器 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 登录拦截，没有登录则跳转到登录界面，有就通过，不需要拦截
     * @param request
     * @param response
     * @param handler
     * @return ture：表示放行当前请求 false：拦截当前请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //根据session域中的uid是否为空，为空就没有登录，不为空就已经登录了
        if(request.getSession().getAttribute("uid") == null){
            //重定向到登录界面
            response.sendRedirect("/web/login.html");
            //拦截当前请求
            return false;
        }
        //放行当前请求
        return true;
    }

}
