package com.zrl.store.mapper;

import com.zrl.store.entity.User;

import java.util.Date;

/* UserMapper接口映射 */
public interface UserMapper {
    /**
     * 添加用户
     * @param user 用户信息
     * @return 数据库影响的行数（根据影响的行数来判断是否添加成功）
     */
    Integer addUser(User user);

    /**
     * 根据用户名来查询用户
     * @param username 用户名
     * @return user：查询成功  null：查询失败
     */
    User findUserByUsername(String username);

    /**
     * 根据uid查找用户
     * @param uid 用户的id
     * @return 查找的用户
     */
//    User findUserByUid(Integer uid);

    /**
     * 根据uid修改账户密码
     * @param uid 用户id
     * @param newPassword 新密码
     * @param ModifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 影响行数
     */
//    Integer updatePasswordByUid(Integer uid, String newPassword, String ModifiedUser, Date modifiedTime);
}
