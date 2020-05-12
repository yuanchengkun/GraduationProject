package com.edu.cuit.competition_management_system.action.teacher;

import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import com.edu.cuit.competition_management_system.dao.userdao.TeamDao;
import com.edu.cuit.competition_management_system.entity.FileUpload;
import com.edu.cuit.competition_management_system.entity.Team;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("TeacherFile")
public class TeacherFileAction {
    @Autowired
    FileDao fileDao;
    @Autowired
    TeamDao teamDao;
    @RequestMapping("myfile")
    public String myfile(){
        return "teacher/file/myfile";
    }
    @RequestMapping("toFileAdd")
    public String toFileAdd(){
        return "teacher/file/file_add";
    }
    @RequestMapping("comfile")
    public String teamfile(){
        return "teacher/file/comfile";
    }

    /**
     * 教师查看竞赛文档
     * @param page
     * @param limit
     * @param session
     * @return
     */
    @RequestMapping("pageComFile")
    @ResponseBody
    public LayuiTable pageTeamFile(String page, String limit, HttpSession session){
        Users users = (Users)session.getAttribute("loginUser");
        LayuiTable layuiTable = new LayuiTable();
        Pageable pager = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(limit));
        if(users.getComtpid()!=null) {//教师有指导竞赛类型
            Page<FileUpload> pagerlist = fileDao.findAllByComtp(users.getComtpid(),pager);
            List<FileUpload> usersList = pagerlist.getContent();
            layuiTable.setCode(0);
            layuiTable.setMsg("ok");
            layuiTable.setCount(pagerlist.getTotalElements());
            layuiTable.setData(usersList);
        }else {
            layuiTable.setCode(0);
            layuiTable.setMsg("没有指导竞赛类型");
        }
        return layuiTable;
    }
    @RequestMapping("selectTeamUrl")
    @ResponseBody
    public LayuiTable selectTeamUrl(HttpSession session){
        Users users = (Users)session.getAttribute("loginUser");
        List<Team> teamList = teamDao.findAllByTeaidAndState(users.getId(),1);
        LayuiTable layuiTable = new LayuiTable();
        layuiTable.setData(teamList);
        return layuiTable;
    }
}
