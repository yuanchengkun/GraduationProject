package com.edu.cuit.competition_management_system.action.student;

import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("UserFile")
public class UserFileAction {
    @Autowired
    FileDao fileDao;
    @RequestMapping("myfile")
    public String myfile(){
        return "";
    }
}
