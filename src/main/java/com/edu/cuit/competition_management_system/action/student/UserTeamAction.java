package com.edu.cuit.competition_management_system.action.student;

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
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.service.ComTpService;
import com.edu.cuit.competition_management_system.service.CompetitionService;
import com.edu.cuit.competition_management_system.service.TeamService;
import com.edu.cuit.competition_management_system.service.TeamUserService;
import com.edu.cuit.competition_management_system.util.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 学生个人团队页面相关控制器
 */
@Controller
@RequestMapping("UserTeam")
public class UserTeamAction {
    @Autowired
    TeamDao teamDao;
    @Autowired
    CompetitionService competitionService;
    @Autowired
    ComTpService comTpService;
    @Autowired
    TeamService teamService;
    @Autowired
    TeamUserService teamUserService;
    @Autowired
    FindUser findUser;
    @Autowired
    TeamUserDao teamUserDao;
    @Autowired
    ComDao comDao;
    /**
     * 跳转到我的团队页面
     * @param session
     * @param request
     * @return
     */
    @RequestMapping("team")
    public String team(HttpSession session, HttpServletRequest request){
        Users users = (Users)session.getAttribute("loginUser");
        users = findUser.findById(users.getId()).get();
        session.setAttribute("loginUser",users);
        if(users.getTeamid()!=null){
            Team team = teamService.findById(users.getTeamid());
            request.setAttribute("userTeam",team);
        }
        return "user/team/teamnow";
    }

    /**
     * 分页查询用户报名竞赛的的有效团队
     * @param session
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("pageTeam")
    @ResponseBody
    public LayuiTable pageTeam(HttpSession session,String page,String limit){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Team> pagerlist = teamDao.findAllByComidAndState(users.getComid(),1,pager);
        List<Team> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }

    /**
     * 查看团员
     * @param id 团队id
     * @return
     */
    @RequestMapping("tuanyuanxueshen")
    @ResponseBody
    public LayuiTable tuanyuanxueshen(int id){
        List<TeamUser> teamUserList = teamUserDao.findAllByTeamidAndState(id,0);
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount((long)teamUserList.size());
        layuiTable.setData(teamUserList);
        return layuiTable;
    }

    /**
     * 查看申请加入的团员
     * @param id 团队id
     * @return
     */
    @RequestMapping("shenqingxueshen")
    @ResponseBody
    public LayuiTable shenqingxueshen(int id,HttpSession session){
        List<TeamUser> teamUserList = teamUserDao.findAllByTeamidAndState(id,1);
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount((long)teamUserList.size());
        layuiTable.setData(teamUserList);
        //判断当前用户是否是小组组长
        Users users = (Users)session.getAttribute("loginUser");
        Team team = teamDao.findById(id).get();
        if(users.getId()==team.getCaptainid()) {//该学生是该团队的队长
            layuiTable.setMsg("ok");
        }
        return layuiTable;
    }

    /**
     * 学生查看申请记录
     * @param session
     * @return
     */
    @RequestMapping("shenqingjilu")
    @ResponseBody
    public LayuiTable shenqingjilu(HttpSession session){
        Users users = (Users)session.getAttribute("loginUser");
        List<TeamUser> teamUserList = teamUserDao.findAllByUseridAndStateAndTeamidNotNull(users.getId(),1);
        List<TeamUser> teamUserList1 = teamUserDao.findAllByUseridAndStateAndTeamidNotNull(users.getId(),2);
        List<TeamUser> teamUserList2 = teamUserDao.findAllByUseridAndStateAndTeamidNotNull(users.getId(),3);
        teamUserList.addAll(teamUserList1);
        teamUserList.addAll(teamUserList2);
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount((long)teamUserList.size());
        layuiTable.setData(teamUserList);
        return layuiTable;
    }

    /**
     * 学生取消申请
     * @param id
     * @return
     */
    @RequestMapping("quxiaoshenqingUrl")
    @ResponseBody
    public String quxiaoshenqingUrl(int id){
        String msg = "";
        try{
            teamUserService.quxiaoshengqing(id,2);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
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
            findUser.save(users);
            //修改用户的其他申请为取消
            teamUserService.quxiaoshengqing(users.getId(),1);
            msg="ok";
        }catch (Exception e){
            msg = "error";
            System.out.println(e.getMessage());
        }finally {
            out.print(msg);
        }
    }

    /**
     * 拒绝学生接入团队
     * @param parm
     * @return
     */
    @RequestMapping("jujue")
    @ResponseBody
    public String jujue(String parm){
        TeamUser teamUser = JSON.parseObject(parm, new TypeReference<TeamUser>() {});
        String msg = "";
        try{
            teamUserService.setTeamUserState(teamUser.getId(),3);//修改申请状态为拒绝
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
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
        TeamUser teamUser1 = teamUserDao.findByTeamidAndUseridAndAndState(id,users.getId(),1);
        if(teamUser1!=null){
            msg="chongfu";
            out.print(msg);
            return;
        }

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


    @RequestMapping("tuichuUrl")
    @ResponseBody
    public String tuichu(int id,HttpSession session){
        String msg = "";
        //判断当前用户是否是小组组长
        Users users = (Users)session.getAttribute("loginUser");
        Team team = teamDao.findById(id).get();
        if(users.getId()==team.getCaptainid()) {//该学生是该团队的队长
            msg = "team";
            return msg;
        }
        try{
            TeamUser teamUser = teamUserDao.findByTeamidAndUseridAndAndState(id,users.getId(),0);
            teamUserDao.delete(teamUser);
            users.setTeamid(null);
            findUser.save(users);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
    }

    /**
     * 创建团队
     * @return
     */
    @RequestMapping("toTeamAdd")
    public String toTeamAdd(){
        return "user/team/team_add";
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
            List<TeamUser> teamUserList = teamUserDao.findAllByUseridAndStateAndTeamidNotNull(users.getId(),1);
            if(teamUserList==null){//用户没有申请加入团队
                teamService.saveTeam(team);
                users.setTeamid(team.getTeamid());
                findUser.save(users);
                TeamUser teamUser = new TeamUser();
                teamUser.setUserid(users.getId());
                teamUser.setTeamid(team.getTeamid());
                teamUser.setState(0);
                teamUserDao.save(teamUser);
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

    @RequestMapping("editPic")
    public String editPic(int id, HttpServletRequest request){
        Team team = teamDao.findById(id).get();
        request.setAttribute("id",id);
        request.setAttribute("team",team);
        return "user/team/teamPic_edit";
    }
    @RequestMapping("updatePic")
    @ResponseBody
    public String updateTeam(String param){
        String msg="";
        Team team = JSON.parseObject(param, new TypeReference<Team>() {});
        try{
            if(team.getTeamid()!=null){
                Team source = teamDao.findById(team.getTeamid()).get();
                UpdateTool.copyNullProperties(source,team);
            }
            teamDao.save(team);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
    }
}
