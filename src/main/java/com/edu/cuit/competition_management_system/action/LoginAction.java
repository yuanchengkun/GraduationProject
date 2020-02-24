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
 * 描述：完成登录注册相关功能的控制器
 */
@Controller
@RequestMapping("LoginAction")
public class LoginAction {
    @Autowired
    UserSign userSign;
    @Autowired
    private DefaultKaptcha captchaProducer;
    /**
     * 获取验证码 的 请求路径
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 完成登录功能
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public String login(HttpServletRequest request,HttpSession session){
        String result="";
        Users users;

        String verifyCode = (String) session.getAttribute("vrifyCode");//session中保存的验证码
        //获得用户输入的用户名密码和验证码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("validatecode");

        if(!verifyCode.equals(code)){
            result="{\"info\":\"验证码错误\",\"status\":\"n\"}";
            return result;
        }
        users = userSign.userSign(username,password);
        if(users!=null) {
            session.setAttribute("loginUser", users);
            return "{\n" +
                    "\t\t\t\"info\":\"数据提交成功！\",\n" +
                    "\t\t\t\"status\":\"y\"\n" +
                    "\t\t }";
        }else {
            result="{\"info\":\"用户名或密码错误\",\"status\":\"n\"}";
            return result;
        }
    }

    /**
     * 注销session中的用户
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpSession session){

        if(session.getAttribute("loginUser")!=null){
            session.removeAttribute("loginUser");

        }
        return "main/index";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
}
