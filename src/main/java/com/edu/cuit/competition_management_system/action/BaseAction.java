package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.UserSign;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * @author yuanck 2016051230
 * 描述：项目启动的基本控制器
 */
@Controller
@RequestMapping("BaseAction")
public class BaseAction {






    @RequestMapping("index")
    public String index(){
        return "index";
    }



    @RequestMapping("daohang")
    public String execute(){

        return "daohang";
    }

    @RequestMapping("main")
    public String main(){
        return "main/index";
    }




}
