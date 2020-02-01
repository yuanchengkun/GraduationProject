package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuanck 2016051230
 * 描述：项目启动的基本控制器
 */
@Controller
@RequestMapping("BaseAction")
public class BaseAction {
    @Autowired
    UserSign userSign;
    @RequestMapping("index")
    public String execute(){
        print1();
        return "index";
    }

   private void print1(){
        Users users;
        users = userSign.userSign("admin","000000");
       System.out.println("登录的用户是"+users.toString());
   }
}
