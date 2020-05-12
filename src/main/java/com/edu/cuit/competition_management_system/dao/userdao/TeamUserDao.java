package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamUserDao extends JpaRepository<TeamUser,Integer> {
    List<TeamUser> findAllByUseridAndStateAndTeamidNotNull(int userid,int state);
    List<TeamUser> findAllByTeamidAndState(int teamid,int state);
    @Modifying
    @Query("update TeamUser u set u.state = ?2 where u.id = ?1")
    void setState(int id, int state); //通过申请id修改用户申请状态
    @Modifying
    @Query("update TeamUser u set u.state = ?2 where u.userid = ?1 and u.state =1")
    void setStateWhthUser(int userid, int state); //通过申请id修改用户申请状态

    TeamUser findByTeamidAndUseridAndAndState(int teamid,int userid,int state);

}
