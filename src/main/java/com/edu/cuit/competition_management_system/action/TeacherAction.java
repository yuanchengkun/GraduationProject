package com.edu.cuit.competition_management_system.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.dao.userdao.ComDao;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.dao.userdao.TeamDao;
import com.edu.cuit.competition_management_system.dao.userdao.TeamUserDao;
import com.edu.cuit.competition_management_system.entity.Competition;
import com.edu.cuit.competition_management_system.entity.Team;
import com.edu.cuit.competition_management_system.entity.TeamUser;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("Teacher")
public class TeacherAction {
    @Autowired
    ComDao comDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamUserDao teamUserDao;
    @Autowired
    FindUser findUser;
    @RequestMapping("home")
    public String home(HttpServletRequest request, HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        LocalDate today = LocalDate.now();
        List<Competition> competitions = comDao.findAllByComtpidAndComtimeIsAfter(users.getComtpid(),today.toString());
        request.setAttribute("competitions",competitions);
        return "teacher/home";
    }
    /**
     * 跳转到我的团队页面
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("team")
    public String team(HttpSession session,HttpServletRequest request){
        Users users = (Users)session.getAttribute("loginUser");
        List<Team> teamList = teamDao.findAllByTeaidAndState(users.getId(),1);
        List<Team> teamList2 = teamDao.findAllByTeaidAndState(users.getId(),2);
        request.setAttribute("teamList",teamList);
        request.setAttribute("teamList2",teamList2);
        return "teacher/team";
    }

    /**
     * 同意创建团队
     * @param param
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("tongyi")
    public void  teamAdd(String parm, HttpServletResponse response, HttpSession session) throws IOException {
        Users users = (Users) session.getAttribute("loginUser");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg = "";
        Team team = JSON.parseObject(parm, new TypeReference<Team>() {});
        team.setState(1);
        Users stu = findUser.findById(team.getCaptainid()).get();
        stu.setTeamid(team.getTeamid());
        TeamUser teamUser = new TeamUser();
        teamUser.setState(0);
        teamUser.setTeamid(team.getTeamid());
        teamUser.setUserid(team.getCaptainid());
        try {
            findUser.save(stu);
            teamDao.save(team);
            teamUserDao.save(teamUser);
            msg = "ok";
        } catch (Exception e) {
            msg = "error";
            System.out.println(e.getMessage());
        } finally {
            out.print(msg);
        }
    }
}
