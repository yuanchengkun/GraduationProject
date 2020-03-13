package com.edu.cuit.competition_management_system.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.dao.userdao.ComDao;
import com.edu.cuit.competition_management_system.dao.userdao.ComTpDao;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.entity.Competition;
import com.edu.cuit.competition_management_system.entity.Competitiontype;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.json.Tablejson;
import com.edu.cuit.competition_management_system.service.ComTpService;
import com.edu.cuit.competition_management_system.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuanck 2016051230
 * 描述：管理页面竞赛模块相关控制器
 */
@Controller
@RequestMapping("AdminCompetition")
public class AdminCompetitionAction {
    @Autowired
    CompetitionService competitionService;
    @Autowired
    ComTpService comTpService;
    @Autowired
    ComTpDao comTpDao;

    @RequestMapping("competitionlist")
    public String competitionList(){
        return "admin/competition/competition_list";
    }
    @RequestMapping("competitiontplist")
    public String competitiontplist(){
        return "admin/competition/competitiontp_list";
    }
    /**
     * 更新竞赛信息
     * @param param
     * @param response
     * @throws IOException
     */
    @RequestMapping("update")
    public void update(String param,HttpServletResponse response)throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        Competition competition = JSON.parseObject(param, new TypeReference<Competition>() {});
        try {
            competitionService.save(competition);
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error1";
            out.print(msg);
        }
    }
    @RequestMapping("competitionAdd")
    public String competitionAdd(){
        return "admin/competition/competition_add";
    }

    @RequestMapping("competitiontpAdd")
    public String competitiontpAdd(){
        return "admin/competition/competitiontp_add";
    }
    /**
     * 更新竞赛种类信息
     * @param param
     * @param response
     * @throws IOException
     */
    @RequestMapping("updatetp")
    public void updatetp(String param,HttpServletResponse response)throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        Competitiontype competitiontype = JSON.parseObject(param, new TypeReference<Competitiontype>() {});
        try {
            comTpService.saveComTp(competitiontype);
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error1";
            out.print(msg);
        }
    }
    /**
     * 分页查询竞赛种类
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping("pageComtp")
    @ResponseBody
    public LayuiTable pageComtp(String limit,String page){
        LayuiTable layuiTable = new LayuiTable();
        Page<Competitiontype> pager = comTpService.findPagercomTp(Integer.parseInt(page)-1,Integer.parseInt(limit));
        List<Competitiontype> CompetitiontypeList = pager.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pager.getTotalElements());
        layuiTable.setData(CompetitiontypeList);
        return layuiTable;
    }
    @RequestMapping("pageCom")
    @ResponseBody
    public LayuiTable pageCom(String limit,String page){
        LayuiTable layuiTable = new LayuiTable();
        Page<Competition> pager = competitionService.findPagercom(Integer.parseInt(page)-1,Integer.parseInt(limit));
        List<Competition> competitions = pager.getContent();

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
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pager.getTotalElements());
        layuiTable.setData(competitions);
        return layuiTable;
    }
}
