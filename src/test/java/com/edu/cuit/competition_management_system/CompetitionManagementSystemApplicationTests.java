package com.edu.cuit.competition_management_system;

import com.edu.cuit.competition_management_system.dao.userdao.TeamDao;
import com.edu.cuit.competition_management_system.dao.userdao.TeamUserDao;
import com.edu.cuit.competition_management_system.entity.Team;
import com.edu.cuit.competition_management_system.entity.TeamUser;
import com.edu.cuit.competition_management_system.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompetitionManagementSystemApplicationTests {
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamService teamService;
    @Autowired
    TeamUserDao teamUserDao;

    @Test
    void contextLoads() {
        Team team = teamDao.findById(2).get();
        System.out.println(team.toString());
    }
    @Test
    void contextLoads1() {
        System.out.println(teamService.findAllTeamWithCom(4).toString());
    }
    @Test
    void teamUserTest() {
        TeamUser teamUser = teamUserDao.findById(2).get();
        System.out.println(teamUser);
    }
}
