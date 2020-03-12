package com.edu.cuit.competition_management_system.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author yuanck 2016051230
 * 描述：管理页面控制器
 */
@Controller
@RequestMapping("Admin")
public class AdminAction {
    @RequestMapping("toAdmin")
    public String toAdmin(){
        return "admin/index";
    }
    @RequestMapping("welcome")
    public String welcome(){
        return "admin/welcome";
    }
}
