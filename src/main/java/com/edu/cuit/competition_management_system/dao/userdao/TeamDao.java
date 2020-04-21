package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.Competition;
import com.edu.cuit.competition_management_system.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamDao extends JpaRepository<Team,Integer> {

    List<Team> findAllByComidAndState(int comid,int state);
    Team findByCaptainidAndState(int cpid,int state);
    //通过指导老师查询所有团队
    List<Team> findAllByTeaidAndState(Integer teaid,Integer state);
}
