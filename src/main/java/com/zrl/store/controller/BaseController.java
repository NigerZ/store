package com.zrl.store.controller;


import com.zrl.store.service.ex.*;
import com.zrl.store.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {

    public static final int OK = 200;

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>();
        if(e instanceof UsernameDuplicateException){
            result.setState(4000);
            result.setMessage("用户名已被使用");
        } else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("用户注册出现未知错误，注册失败");
        }else if(e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("找不到用户");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("密码错误");
        }else if(e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("用户修改异常");
        }
        return result;
    }

    /**
     * 获取session中的uid数据
     * @param session session域对象
     * @return uid
     */
    public Integer getUid(HttpSession session){
        return Integer.parseInt(session.getAttribute("uid").toString());
    }

    /**
     * 获取session域中的username数据
     * @param session session域对象
     * @return username
     */
    public String getUsername(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
