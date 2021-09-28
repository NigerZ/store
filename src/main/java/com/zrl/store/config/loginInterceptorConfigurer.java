package com.zrl.store.config;

import com.zrl.store.Interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理器拦截器的注册
 */
@Configuration
public class loginInterceptorConfigurer implements WebMvcConfigurer {

    //创建自定义的拦截器
    HandlerInterceptor interceptor = new LoginInterceptor();


    /**
     * 配置拦截器，完成拦截器对象的注册
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 白名单
        List<String> whitelist = new ArrayList<String>();
        whitelist.add("/bootstrap3/**");
        whitelist.add("/css/**");
        whitelist.add("/images/**");
        whitelist.add("/js/**");
        whitelist.add("/web/register.html");
        whitelist.add("/web/login.html");
        whitelist.add("/web/index.html");
        whitelist.add("/web/product.html");
        whitelist.add("/users/register");
        whitelist.add("/users/login");
        whitelist.add("/districts/**");
        whitelist.add("/products/**");
        //向注册器对象中添加拦截器对象，完成拦截器对象的注册
        //addPathwhitelist()表示添加要拦截的url
        //excludePathwhitelist()表示不需要拦截的url
        registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns(whitelist);
    }
}
