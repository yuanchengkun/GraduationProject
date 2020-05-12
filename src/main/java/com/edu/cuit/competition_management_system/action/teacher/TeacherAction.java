package com.edu.cuit.competition_management_system.action.teacher;

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

    @RequestMapping("index")
    public String index(HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users!=null&&users.getType()==2){
            return "teacher/index";
        }else
            return "redirect:/LoginAction/toLogin";
    }
    @RequestMapping("home")
    public String home(HttpServletRequest request, HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        LocalDate today = LocalDate.now();
        List<Competition> competitions = comDao.findAllByComtpidAndComtimeIsAfter(users.getComtpid(),today.toString());
        request.setAttribute("competitions",competitions);
        return "teacher/home";
    }
    /**
     * 初始化首页数据
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("welcome")
    public String welcome(HttpServletRequest request,HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users.getComtpid()==null){

        }else {
            LocalDate today = LocalDate.now();
            //找出指导老师指导竞赛类型还未开始的竞赛
            List<Competition> competitions = comDao.findAllByComtpidAndComtimeIsAfter(users.getComtpid(),today.toString());
            request.setAttribute("competitions",competitions);
        }
        return "teacher/welcome";
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
        List<Team> teamList  = teamDao.findAllByTeaidAndState(users.getId(),1);
        List<Team> teamList2 = teamDao.findAllByTeaidAndState(users.getId(),2);
        request.setAttribute("teamList",teamList);
        request.setAttribute("teamList2",teamList2);
        return "teacher/team";
    }


}
