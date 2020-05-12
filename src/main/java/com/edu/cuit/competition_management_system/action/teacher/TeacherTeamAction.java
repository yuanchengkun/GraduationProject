package com.edu.cuit.competition_management_system.action.teacher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.dao.userdao.TeamDao;
import com.edu.cuit.competition_management_system.dao.userdao.TeamUserDao;
import com.edu.cuit.competition_management_system.entity.Team;
import com.edu.cuit.competition_management_system.entity.TeamUser;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.LayuiTable;
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

@Controller
@RequestMapping("TeacherTeam")
public class TeacherTeamAction {
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamUserDao teamUserDao;
    @Autowired
    FindUser findUser;
    /**
     * 跳转到我的团队页面
     * @return
     */
    @RequestMapping("team")
    public String team(){
        return "teacher/team/team";
    }
    @RequestMapping("teampizhun")
    public String teampizhun(){
        return "teacher/team/teampizhun";
    }
    @RequestMapping("pageTeam")
    @ResponseBody
    public LayuiTable pagerTeam(HttpSession session,String page,String limit){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Team> pagerlist = teamDao.findAllByTeaidAndState(users.getId(),1,pager);
        List<Team> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }
    @RequestMapping("pageTeampizhun")
    @ResponseBody
    public LayuiTable pageTeampizhun(HttpSession session,String page,String limit){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Team> pagerlist = teamDao.findAllByTeaidAndState(users.getId(),2,pager);
        List<Team> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }
    @RequestMapping("forwardTeam")
    public String forwardTeam(int id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "teacher/team/teamusert";
    }
    /**
     * 查看申请学生
     * @param id 团队id
     * @return
     */
    @RequestMapping("shenqingjilu")
    @ResponseBody
    public LayuiTable tuanyuanxueshen(int id){
        List<TeamUser> teamUserList = teamUserDao.findAllByTeamidAndState(id,1);
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount((long)teamUserList.size());
        layuiTable.setData(teamUserList);
        return layuiTable;
    }

    /**
     * 同意创建团队
     * @param parm
     * @param response
     * @throws IOException
     */
    @RequestMapping("tongyi")
    public void  teamAdd(String parm, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg = "";
        Team team = JSON.parseObject(parm, new TypeReference<Team>() {});
        team.setState(1);
        try {
            teamDao.save(team);
            msg = "ok";
        } catch (Exception e) {
            msg = "error";
            System.out.println(e.getMessage());
        } finally {
            out.print(msg);
        }
    }
    /**
     * 拒绝创建团队
     * @param parm
     * @param response
     * @throws IOException
     */
    @RequestMapping("jujueUrl")
    public void  jujueUrl(String parm, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg = "";
        Team team = JSON.parseObject(parm, new TypeReference<Team>() {});
        team.setState(3);
        Users users = findUser.findById(team.getCaptainid()).get();
        users.setTeamid(null);
        try {
            teamDao.save(team);
            findUser.save(users);
            msg = "ok";
        } catch (Exception e) {
            msg = "error";
            System.out.println(e.getMessage());
        } finally {
            out.print(msg);
        }
    }

    @RequestMapping("toPastteam")
    public String toPastteam(){
        return "teacher/team/pastteam";
    }
    @RequestMapping("pagePastTeam")
    @ResponseBody
    public LayuiTable pagePastTeam(HttpSession session,String page,String limit){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Team> pagerlist = teamDao.findAllByTeaidAndState(users.getId(),0,pager);
        List<Team> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }
}
