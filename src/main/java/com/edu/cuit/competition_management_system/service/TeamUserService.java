package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.TeamUser;

import java.util.List;

public interface TeamUserService {
    void setTeamUserState(int id,int state);//通过id修改状态
    void saveTeamUser(TeamUser teamUser);//保存申请信息
    void quxiaoshengqing(int id,int state);//用户取消申请加入团队 id 1:用户id id 2申请id
}
