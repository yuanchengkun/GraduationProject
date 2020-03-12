package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.Users;

import java.util.List;

/**
 * 提供用户登录注册相关的服务
 * @author yuanck 2016051230
 */
public interface UserSign {
    Users userSign(String username,String password);//用户登录
    public boolean checkUsernameIsExist(String username);//检查用户名是否被占用
    public Users save(Users user)throws Exception; //注册用户
    public List<Users> findAllByType(int i);//通过类型查询所有用户
    public void setUserState(int id,int state);//通过id修改用户状态
    public List<Users> findAllDelUser();//查询所有无效状态用户
    public void setUserPass(int id,String pass);//根据用户id修改用户密码
    public void setUserCom(int id,int comid);//根据用户id修改用户选择的竞赛
}
