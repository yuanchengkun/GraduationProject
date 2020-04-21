package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.TeamDao;
import com.edu.cuit.competition_management_system.entity.Team;
import com.edu.cuit.competition_management_system.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamDao teamDao;
    @Override
    public List<Team> findAllTeamWithCom(int comid) {
        return teamDao.findAllByComidAndState(comid,1);
    }

    @Override
    public Team findById(int teamid) {
        return teamDao.findById(teamid).get();
    }
    @Transactional
    @Override
    public void saveTeam(Team team) {
        teamDao.save(team);
    }

    @Override
    public boolean isUserCreat(int uid) {
        if(teamDao.findByCaptainidAndState(uid,2)!=null)
           return true;
        else return false;
    }
}
