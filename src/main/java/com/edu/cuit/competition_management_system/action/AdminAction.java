package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.entity.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author yuanck 2016051230
 * 描述：管理页面控制器
 */
@Controller
@RequestMapping("Admin")
public class AdminAction {
    @RequestMapping("toAdmin")
    public String toAdmin(HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users!=null&&users.getType()==0){
            return "admin/index";
        }else
            return "login";
    }
    @RequestMapping("welcome")
    public String welcome(){
        return "admin/welcome";
    }
}
