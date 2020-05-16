package com.edu.cuit.competition_management_system.action.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.edu.cuit.competition_management_system.json.Tablejson;
import com.edu.cuit.competition_management_system.service.ComTpService;
import com.edu.cuit.competition_management_system.service.CompetitionService;
import com.edu.cuit.competition_management_system.service.UserSign;
import com.edu.cuit.competition_management_system.util.FileUploadUtils;
import com.edu.cuit.competition_management_system.util.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanck 2016051230
 * 描述：管理页面用户模块相关控制器
 */
@Controller
@RequestMapping("AdminMember")
public class AdminMemberAction {
    @Autowired
    UserSign userSign;
    @Autowired
    CompetitionService competitionService;
    @Autowired
    ComTpService comTpService;
    @Autowired
    FindUser findUser;
    @Autowired
    TeamDao teamDao;
    @Autowired
    ComDao comDao;
    @Autowired
    TeamUserDao teamUserDao;

    @RequestMapping("member_list")
    public String member_list(){
        return "admin/member/member-tea";
    }
    @RequestMapping("member_list1")
    public String member_list1(){
        return "admin/member/member_stu";
    }

    @RequestMapping("member_team")
    public String member_tean(){
        return "admin/member/member_team";
    }
    @RequestMapping("member_del")
    public String member_del(){
        return "admin/member/member-del";
    }

    /**
     * 查询所有有效状态用户数据
     * @param type 获取查询的用户类型
     * @return 如下json格式的学生列表
     * {
     *   "code": 0,
     *   "msg": "",
     *   "count": 1000,
     *   "data": [{}, {}]
     * }
     */
    @RequestMapping("user")
    @ResponseBody
    public String student(String type){
        Tablejson tb = new Tablejson();
        tb.setData(userSign.findAllByType(Integer.parseInt(type)));
        return tb.getDate();
    }

    /**
     * 分页查询所有学生或老师用户
     * @param type 1学生 2老师
     * @param limit 每页数据
     * @param page 当前页面
     * @return
     */
    @RequestMapping("pageUser")
    @ResponseBody
    public LayuiTable pageUser(String type,String truename,String username,String card,String limit,String page){
        LayuiTable layuiTable = new LayuiTable();
        Users users = new Users();;
        //定义查询模板
        truename=(truename=="")?null:truename;
        username=(username=="")?null:username;
        card=(card=="")?null:card;
        users.setTruename(truename);
        users.setUsername(username);
        if(card!=null)
        users.setCard(Integer.parseInt(card));
        users.setType(Integer.parseInt(type));
        users.setState(1);
      //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        Example<Users> ex = Example.of(users, matcher);
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Users> pagerlist = findUser.findAll(ex,pager);
        //封装数据
        List<Users> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);
        return layuiTable;
    }
    /**
     * 查询所有无效状态数据
     *
     * @return
     */
    @RequestMapping("findDel")
    @ResponseBody
    public String findDel(){
        Tablejson tb = new Tablejson();
        tb.setData(userSign.findAllDelUser());
        return tb.getDate();
    }
    /**
     * 根据用户id修改用户状态
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("setUserState")
    public void setUserState(String id,String state,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try {
            userSign.setUserState(Integer.parseInt(id),Integer.parseInt(state));
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error";
            out.print(msg);
        }
    }
    @RequestMapping("memberAdd")
    public String memberAdd(){
        return "admin/member/member-add";
    }

    /**
     * 根据用户id重置用户密码
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("resetPw")
    public void resetPw(String id,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try {
            userSign.setUserPass(Integer.parseInt(id),"000000");
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error";
            out.print(msg);
        }
    }

    /**
     * 修改用户信息
     * @param param
     * @param response
     * @throws IOException
     */
    @RequestMapping("update")
    public void update(String param,HttpServletResponse response)throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        Users user = JSON.parseObject(param, new TypeReference<Users>() {});
        try {
            userSign.save(user);
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error";
            out.print(msg);
        }
    }

    /**
     * 获得所有的竞赛列表
     * @return
     */
    @ResponseBody
    @RequestMapping("selectCom")
    public String selectCom(){
        Tablejson tb = new Tablejson();
        tb.setData(competitionService.findAll());
        return tb.getDate();
    }

    /**
     * 更新用户选择的竞赛
     * @param id 用户id
     * @param comid 竞赛id
     * @param response
     * @throws IOException
     */
    @RequestMapping("updateCOm")
    public void updateCOm(String id,String comid,HttpServletResponse response)throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try {
            userSign.setUserCom(Integer.parseInt(id),Integer.parseInt(comid));
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error";
            out.print(msg);
        }
    }

    /**
     * 更新老师指导的竞赛类型
     * @param id 用户id
     * @param comtpid 竞赛类型id
     * @param response
     * @throws IOException
     */
    @RequestMapping("updateCOmtp")
    public void updatecomty(String id,String comtpid,HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try {
            userSign.setUserComtp(Integer.parseInt(id),Integer.parseInt(comtpid));
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error";
            out.print(msg);
        }
    }
    /**
     * 获得所有的竞赛种类列表
     * @return
     */
    @ResponseBody
    @RequestMapping("selectComtp")
    public String selectComtp(){
        Tablejson tb = new Tablejson();
        tb.setData(comTpService.findAllComTp());
        return tb.getDate();
    }
    @ResponseBody
    @RequestMapping("deleteAll")
    public String deleteAll(String param){
        ArrayList<Users> userList  = JSON.parseObject(param, new TypeReference<ArrayList<Users>>(){});
        String msg="";
        try {
            userSign.setUserState(userList,0);
            msg="ok";
        }catch (Exception e){
            msg="error";
        }
        return msg;
    }

    /**
     * 分页查询已经报了名的学生列表
     * @param page
     * @param limit
     * @param type
     * @return
     */
    @RequestMapping("baominStu")
    @ResponseBody
    public LayuiTable baominStu(String page,String limit,int type){
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Users> pagerlist = findUser.findAllByComidNotNullAndType(type,pager);
        List<Users> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);

        return layuiTable;
    }

    @RequestMapping("pageTeam")
    @ResponseBody
    public LayuiTable pageTeam(String page,String limit){
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        Page<Team> pagerlist = teamDao.findAll(pager);
        List<Team> usersList = pagerlist.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pagerlist.getTotalElements());
        layuiTable.setData(usersList);

        return layuiTable;
    }
    @ResponseBody
    @RequestMapping("selectTeacher")
    public LayuiTable selectTeacher(int comid){
        LayuiTable layuiTable = new LayuiTable();
        Competition competition = comDao.findById(comid).get();
        //查到所有指导该竞赛类型的老师
        List<Users> tea = comTpService.findAllTeaWithComTp(competition.getComtpid());
        layuiTable.setData(tea);
        return layuiTable;
    }
    @RequestMapping("updateTeam")
    @ResponseBody
    public String updateTeam(String param){
        String msg="";
        Team team = JSON.parseObject(param, new TypeReference<Team>() {});
        try{
            if(team.getTeamid()!=null){
                Team source = teamDao.findById(team.getTeamid()).get();
                UpdateTool.copyNullProperties(source,team);
                //如果将团队状态变更为失效和申请失败需要将想对应的用户团队字段置空
                if(team.getState()==0||team.getState()==3){
                    List<Users> usersList = findUser.findAllByTeamid(team.getTeamid());
                    for (Users u:usersList) {
                        u.setTeamid(null);
                        findUser.save(u);
                    }
                }

            }
            teamDao.save(team);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
    }
    @RequestMapping("deleteTeam")
    @ResponseBody
    public String deleteTeam(int teamid){
        String msg = "";
        try{
            teamDao.deleteById(teamid);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
    }
    @RequestMapping("forwardTeam")
    public String forwardTeam(int id, HttpServletRequest request){
        request.setAttribute("id",id);
        return "admin/member/teamusert";
    }
    @RequestMapping("teamuser")
    @ResponseBody
    public LayuiTable teamuser(int id){
        List<TeamUser> teamUserList = teamUserDao.findAllByTeamidAndState(id,0);
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setCode(0);
        layuiTable.setMsg("");
        layuiTable.setCount((long)teamUserList.size());
        layuiTable.setData(teamUserList);
        return layuiTable;
    }

}
