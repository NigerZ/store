package com.zrl.store.service.impl;

import com.zrl.store.entity.User;
import com.zrl.store.mapper.UserMapper;
import com.zrl.store.service.IUserService;
import com.zrl.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
/* 处理用户数据的业务层实现类 */
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void register(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        //调用持久层的User findByUserName（String username）方法，根据用户名查询用户数据
        User result = userMapper.findUserByUsername(username);
        //判断查询结果是否不为null
        if (result != null) {
            //是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicateException("尝试注册的用户名[" + username + "]已经被占用");
        }
        //创建当前时间对象
        Date now = new Date();
        //补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        //补全数据：盐值
        user.setSalt(salt);
        //补全数据：isDelete(0)
        user.setIsDelete(0);
        //补全数据：4项日志属性
        user.setCreatedUser(username);
        user.setCreatedTime(now);
        user.setModifiedUser(username);
        user.setModifiedTime(now);

        //表示用户名没有被占用，添加用户
        //调用持久层的addUser()，执行注册获取返回值（受影响的行数）
        Integer rows = userMapper.addUser(user);
        if(rows != 1){
            throw new InsertException("添加用户数据出现未知错误，请联系管理员");
        }
    }

    @Override
    public User login(String username, String newPassword) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findUserByUsername(username);
        // 判断查询结果是否为null
        if(result == null){
        // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户不存在！");
        }
        // 判断查询结果中的isDelete是否为1
        if(result.getIsDelete() == 1){
        // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户不存在！");
        }
        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(newPassword, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if(!result.getPassword().equals(md5Password)){
        // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("密码错误！");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username、avatar封装到新的user对象中
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        // 返回新的user对象
        return user;
    }

/*    @Override
    public void changePassword(Integer uid, String oldPassword, String newPassword) {
        //根据uid查找用户信息
        User result = userMapper.findUserByUid(uid);
        //判断用户是否存在
        if(result == null){
            throw new UserNotFoundException("用户不存在");
        }
        //判断用户是否已注销
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户已注销");
        }
        //获取用户的盐值
        String salt = result.getSalt();
        //计算用户的原始md5密码
        String oldMd5Password = getMd5Password(oldPassword, salt);
        //判断用户输入的密码是否和原来的密码一致
        if(result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码验证失败");
        }
        //对新密码进行加密
        String newMd5Password = getMd5Password(newPassword, salt);
        //修改时间
        Date modifideTime = new Date();
        //进行修改密码
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, result.getUsername(), modifideTime);
        //判断密码是否修改成功
        if(rows != 1){
            throw new UpdateException("用户更新密码出现异常");
        }
    }*/

    /**
     * 加密规则
     * @param password 原始密码
     * @param salt 盐值
     */
    private String getMd5Password(String password, String salt) {
        /*
        * 加密规则：
        * 1、无视原始密码的强度
        * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
        * 3、循环加密三次
        */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
