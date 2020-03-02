package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.Users;

/**
 * 提供用户登录注册相关的服务
 * @author yuanck 2016051230
 */
public interface UserSign {
    Users userSign(String username,String password);//用户登录
    public boolean checkUsernameIsExist(String username);//检查用户名是否被占用
    public Users save(Users user)throws Exception; //注册用户
}
