package com.edu.cuit.competition_management_system.dao.userdao;


import com.edu.cuit.competition_management_system.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamDao extends JpaRepository<Team,Integer> {

    List<Team> findAllByComidAndState(int comid,int state);
    Team findByCaptainidAndState(int cpid,int state);
    //通过指导老师查询所有团队
    List<Team> findAllByTeaidAndState(Integer teaid,Integer state);
    Page<Team> findAllByTeaidAndState(Integer teaid,Integer state,Pageable pageable);
    Page<Team> findAllByComidAndState(int comid, int state, Pageable pageable);
    //查询所有有效且有宣传图片的团队
    Page<Team> findAllByStateAndPicNotNull(int state,Pageable pageable);

    @Query(value = "SELECT * from team WHERE teamid in (SELECT teamid FROM teamuser WHERE userid = ?1 AND state = 0) AND state = 0",nativeQuery = true)
    Page<Team> findStuPastTeam(int userid, Pageable pageable);
}
