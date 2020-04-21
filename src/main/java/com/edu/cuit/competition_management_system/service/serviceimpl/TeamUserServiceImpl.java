package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.TeamUserDao;
import com.edu.cuit.competition_management_system.entity.TeamUser;
import com.edu.cuit.competition_management_system.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TeamUserServiceImpl implements TeamUserService {
    @Autowired
    TeamUserDao teamUserDao;

    @Transactional
    @Override
    public void saveTeamUser(TeamUser teamUser) {
        teamUserDao.save(teamUser);
    }

    /**
     *
     * @param id 1全部取消 2单个取消
     * @param state
     */
    @Transactional
    @Override
    public void quxiaoshengqing(int id, int state) {
        if(state==2){//用户单个取消
            teamUserDao.setState(id,2);
        }else if(state==1){//用户全部取消
            teamUserDao.setStateWhthUser(id,2);
        }
    }

    @Transactional
    @Override
    public void setTeamUserState(int id, int state) {
        teamUserDao.setState(id,state);
    }
}
