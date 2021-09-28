package com.zrl.store.controller;

import com.zrl.store.entity.User;
import com.zrl.store.service.IUserService;
import com.zrl.store.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/register")
    public JsonResult<Void> register(User user) {
        userService.register(user);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        // 调用业务对象的方法执行登录，并获取返回值
        User result = userService.login(username, password);
        //将uid和username存储在session域中
        session.setAttribute("uid",result.getUid());
        session.setAttribute("username",result.getUsername());
        // 将以上返回值和状态码OK封装到响应结果中并返回
//        System.out.println(getUid(session)+"@"+getUsername(session));
        return new JsonResult<>(OK, result);
    }

//    @RequestMapping("/change_password")
//    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session){
//        //获取到session域中的uid
//        Integer uid = (Integer) session.getAttribute("uid");
//        //修改密码的业务
//        userService.changePassword(uid, oldPassword,newPassword);
//        //返回成功
//        return new JsonResult<>(OK);
//    }

}
