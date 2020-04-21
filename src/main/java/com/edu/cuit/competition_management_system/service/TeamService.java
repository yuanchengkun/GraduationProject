package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.Team;

import java.util.List;

public interface TeamService {
    List<Team> findAllTeamWithCom(int comid);//根据竞赛查询所有队伍
    Team findById(int teamid);//通过团队id查询团队
    void saveTeam(Team team);
    boolean isUserCreat(int uid);//用户是否已经申请创建团队
}
