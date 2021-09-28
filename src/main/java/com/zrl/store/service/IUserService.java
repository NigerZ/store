package com.zrl.store.service;

import com.zrl.store.entity.User;
/* 处理用户数据的业务层接口 */
public interface IUserService {

    /**
     * 用户注册
     * @param user 用户数据
     */
    void register(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    User login(String username, String password);

    /**
     * 根据用户的id修改密码
     * @param uid 用户id
     * @param oldPassword 原密码
     * @param newPassword 新密码
     */
//    void changePassword(Integer uid, String oldPassword, String newPassword);
}
