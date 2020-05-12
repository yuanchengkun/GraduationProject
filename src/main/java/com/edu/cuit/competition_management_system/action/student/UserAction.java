package com.edu.cuit.competition_management_system.action.student;

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
    FindUser findUser;
    @Autowired
    ComTpService comTpService;
    @Autowired
    TeamUserService teamUserService;

    @RequestMapping("index")
    public String index(HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users!=null&&users.getType()==1){
            return "user/index";
        }else
            return "redirect:/LoginAction/toLogin";
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
        if(users.getComid()==null){
            request.setAttribute("userCom",null);
            //查询所有的指导老师信息
            List<Users> tea = findUser.findAllByTypeAndComtpidNotNull(2);
            request.setAttribute("teaList",tea);
        }else {
            //查询该竞赛信息
            Competition competition = comDao.findById(users.getComid()).get();
            //查到所有指导该竞赛类型的老师
            List<Users> tea = comTpService.findAllTeaWithComTp(competition.getComtpid());
            competition.setCompetitiontype(comTpDao.findById(competition.getComtpid()).get());
            request.setAttribute("userCom",competition);
            request.setAttribute("teaList",tea);
        }
        return "user/welcome";
    }

    /**
     * 修改用户信息
     * @param param
     * @param session
     * @return
     */
    @RequestMapping("updataUser")
    @ResponseBody
    public String updataUser(String param,HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        Users users1 = JSON.parseObject(param, new TypeReference<Users>() {});
        users.setTruename(users1.getTruename());
        users.setEmail(users1.getEmail());
        String msg = "";
        try{
            findUser.save(users);
            msg="ok";
        }catch (Exception e){
            msg="error";
            System.out.println(e.getMessage());
        }
        return msg;
    }

    /**
     * 用户取消竞赛报名
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("quxiaobaoming")
    public void  quxiaobaoming(HttpServletResponse response,HttpSession session) throws IOException{
        Users users = (Users)session.getAttribute("loginUser");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        try{
            if(users.getTeamid()==null) {
                users.setComid(null);
                //取消用户所有的团队申请
                teamUserService.quxiaoshengqing(users.getId(),1);
                userSign.save(users);
                session.setAttribute("loginUser",users);
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



}
