package com.edu.cuit.competition_management_system.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.dao.userdao.*;
import com.edu.cuit.competition_management_system.entity.*;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuanck 2016051230
 * 描述：用户个人中心页面控制器
 */
@Controller
@RequestMapping("User")
public class UserAction {
    @Autowired
    CompetitionService competitionService;
    @Autowired
    ComTpDao comTpDao;
    @Autowired
    ComDao comDao;
    @Autowired
    UserSign userSign;
    @Autowired
    ComTpService comTpService;
    @Autowired
    TeamUserDao teamUserDao;
    @Autowired
    TeamService teamService;
    @Autowired
    TeamUserService teamUserService;
    @Autowired
    TeamDao teamDao;
    @Autowired
    FileDao fileDao;

    @RequestMapping("mine")
    public String mine(HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users!=null) {
            if(users.getType()==0)
                return "admin/index";
            if(users.getType()==2)
                return "teacher/index";
            else
                return "user/index";
        }else {
            return "redirect:/LoginAction/toLogin";
        }
    }
    @RequestMapping("home")
    public String home(HttpServletRequest request,HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users.getComid()==null){
            request.setAttribute("userCom",null);
        }else {
            //查询该竞赛信息
            Competition competition = comDao.findById(users.getComid()).get();
            //查到所有指导该竞赛类型的老师
            List<Users> tea = comTpService.findAllTeaWithComTp(competition.getComtpid());
            competition.setCompetitiontype(comTpDao.findById(competition.getComtpid()).get());
            request.setAttribute("userCom",competition);
            request.setAttribute("teaList",tea);
        }
        return "user/home";
    }

    /**
     * 跳转到竞赛报名页面
     * @param id 报名的竞赛种类id
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("signUp")
    public String signUp(Integer id, HttpServletRequest request,HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users!=null){
            List<Competition> competitions = competitionService.findAllComInTimeWithComtp(id);
            Iterator<Competition> it = competitions.iterator();
            int n;
            Competition competition;
            while (it.hasNext()){
                competition = (Competition)it.next();
                if(competition.getComtpid()!=null){
                    n = competition.getComtpid();
                    competition.setCompetitiontype(comTpDao.findById(n).get());
                }
            }
            request.setAttribute("competitionInTime",competitions);
            return "main/competitionSignUp";
        }else
            return "redirect:/LoginAction/toLogin";
    }

    /**
     * 用户竞赛报名
     * @param comid 报名的竞赛id
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("baoming")
    public void baoming(Integer comid, HttpServletResponse response,HttpSession session)throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        Users users = (Users)session.getAttribute("loginUser");
        String msg="";
        try{
            if(userSign.userBaoming(users.getId(),comid)){
                msg="ok";
                users.setComid(comid);
                session.setAttribute("loginUser",users);
            }else{
                msg="chongfu";
            }
        }catch (Exception e){
            msg = "error";
            System.out.println(e.getMessage());
        }finally {
            out.print(msg);
        }
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
        if(users.getTeamid()==null){//当前没有参加团队
            if(users.getComid()==null){//当前还没有报名竞赛

            }else {//已经报名了竞赛
                request.setAttribute("userCom",1);
                //写入同竞赛的团队信息到request
                List<Team> teamList = teamService.findAllTeamWithCom(users.getComid());
                request.setAttribute("teamList",teamList);
                //查找我的团队申请记录
                List<TeamUser> teamUserList = teamUserDao.findAllByUserid(users.getId());
                request.setAttribute("teamUserList",teamUserList);
                //查找我的团队创建记录
                Team team = teamDao.findByCaptainidAndState(users.getId(),2);
                request.setAttribute("creatTeam",team);
            }
        }else {//当前参加了团队
            Team team = teamService.findById(users.getTeamid());
            request.setAttribute("userTeam",team);
            if(users.getId()==team.getCaptainid()){//该学生是该团队的队长
                //查询该团队所有申请中的学生
                List<TeamUser> teamUserList =teamUserDao.findAllByTeamidAndState(users.getTeamid(),1);
                request.setAttribute("teamUserList",teamUserList);
            }
            //查询该团队所有成员
            List<TeamUser> teamUsers = teamUserDao.findAllByTeamidAndState(users.getTeamid(),0);
            request.setAttribute("teamUsers",teamUsers);
        }
        return "user/team";
    }

    /**
     * 同意用户申请加入团队
     * @param parm 申请表记录
     * @param response
     * @throws IOException
     */
    @RequestMapping("tongyi")
    public void tongyi(String parm,HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        TeamUser teamUser = JSON.parseObject(parm, new TypeReference<TeamUser>() {});
        String msg="";
        try{
                teamUserService.setTeamUserState(teamUser.getId(),0);//修改申请状态为通过
            //修改用户表的teamid
            Users users = teamUser.getStudent();
            users.setTeamid(teamUser.getTeamid());
            userSign.save(users);
                msg="ok";
        }catch (Exception e){
            msg = "error";
            System.out.println(e.getMessage());
        }finally {
            out.print(msg);
        }
    }

    /**
     * 用户报名参加团队
     * @param id 团队id
     * @param response
     * @throws IOException
     */
    @RequestMapping("baomingTeam")
    public void  baoming(int id,HttpServletResponse response,HttpSession session) throws IOException{
        Users users = (Users)session.getAttribute("loginUser");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try{
            TeamUser teamUser = new TeamUser();
            teamUser.setTeamid(id);
            teamUser.setUserid(users.getId());
            teamUser.setState(1);
            teamUserService.saveTeamUser(teamUser);
            msg="ok";
        }catch (Exception e){
            msg = "error";
            System.out.println(e.getMessage());
        }finally {
            out.print(msg);
        }
    }

    @RequestMapping("quxiaobaoming")
    public void  quxiaobaoming(HttpServletResponse response,HttpSession session) throws IOException{
        Users users = (Users)session.getAttribute("loginUser");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try{
            if(users.getTeamid()==null) {
                users.setComid(null);
                teamUserService.quxiaoshengqing(users.getId(),1);
                userSign.save(users);
                msg = "ok";
            }else {
                msg = "team";
            }
        }catch (Exception e){
            msg = "error";
            System.out.println(e);
        }finally {
            out.print(msg);
        }
    }

    /**
     * 创建团队
     * @return
     */
    @RequestMapping("toTeamAdd")
    public String toTeamAdd(){
        return "user/team_add";
    }
    @ResponseBody
    @RequestMapping("selectTeacher")
    public LayuiTable selectTeacher(HttpSession session){
        LayuiTable layuiTable = new LayuiTable();
        Users users = (Users)session.getAttribute("loginUser");
        Competition competition = comDao.findById(users.getComid()).get();
        //查到所有指导该竞赛类型的老师
        List<Users> tea = comTpService.findAllTeaWithComTp(competition.getComtpid());
        layuiTable.setData(tea);
        return layuiTable;
    }
    @RequestMapping("teamAdd")
    public void  teamAdd(String param,HttpServletResponse response,HttpSession session) throws IOException{
        Users users = (Users)session.getAttribute("loginUser");
        response.setContentType("text/html;charset=utf-8");
        Team team = JSON.parseObject(param, new TypeReference<Team>() {});
        team.setState(2);
        team.setComid(users.getComid());
        team.setCaptainid(users.getId());
        PrintWriter out = response.getWriter();
        String msg="";
        try{
            if(!teamService.isUserCreat(users.getId())){//用户没有申请过团队
                teamService.saveTeam(team);
                msg="ok";
            }
            else {//只能申请一个团队
                msg="chongfu";
            }
        }catch (Exception e){
            msg = "error";
            System.out.println(e.getMessage());
        }finally {
            out.print(msg);
        }

    }

    /**
     * 跳转到用户文档查询页面
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("toFile")
    public String toFile(HttpSession session,HttpServletRequest request){
        Users users = (Users)session.getAttribute("loginUser");
        //查找公开文件
        List<FileUpload> publicFile = fileDao.findAllByTeamid(null);
        //查找组内文件
        List<FileUpload> teamFile=null;
        if(users.getTeamid()!=null){
            teamFile = fileDao.findAllByTeamid(users.getTeamid());
        }
        //查找个人文件
        List<FileUpload> personFile = fileDao.findAllByUserid(users.getId());
        request.setAttribute("publicFile",publicFile);
        request.setAttribute("teamFile",teamFile);
        request.setAttribute("personFile",personFile);
        return "user/file";
    }
}
