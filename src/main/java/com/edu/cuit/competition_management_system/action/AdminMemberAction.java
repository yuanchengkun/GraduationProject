package com.edu.cuit.competition_management_system.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.entity.Competition;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.Tablejson;
import com.edu.cuit.competition_management_system.service.CompetitionService;
import com.edu.cuit.competition_management_system.service.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;

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

    @RequestMapping("member_list")
    public String member_list(){
        return "admin/member/member-tea";
    }
    @RequestMapping("member_list1")
    public String member_list1(){
        return "admin/member/member_stu";
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
        //Competition competition = JSON.parseObject(param,new TypeReference<Competition>(){});
        //System.out.println(competition.toString());
        try {
            userSign.save(user);
            //competitionService.save(competition);
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
}
