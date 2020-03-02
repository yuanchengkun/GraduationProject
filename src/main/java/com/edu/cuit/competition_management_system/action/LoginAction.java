package com.edu.cuit.competition_management_system.action;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.UserSign;
import com.edu.cuit.competition_management_system.util.SendEmail;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

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

    @RequestMapping("toReg")
    public String toReg(){
        return "reg";
    }

    /**
     * 发送邮件
     * @param toEmail 发送的邮件的地址
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("sendValidateEmail")
    public void sendValidateEmail(String toEmail,HttpSession session,HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        String msg="";
        //随机生成一个四位的验证码，验证一般有数字和字母组合而成。
        String randomCode = RandomUtil.randomString(4);
        try {
            SendEmail.send(toEmail,randomCode);
            //MailUtil.send(toEmail, "会议与培训网验证码", "您的邮箱验证码是："+randomCode, false);
            //把验证码保存到session中。
            session.setAttribute("randomCode",randomCode);
            //5分钟后删除session中的验证码
            this.removeAttrbute(session, "randomCode");
            msg="send_ok";
            out.print(msg);
        } catch (Exception e) {
            e.printStackTrace();
            msg="send_error";
            out.print(msg);

        }
    }
    /**
     * 设置5分钟后删除session中的验证码
     * @param session
     * @param attrName
     */
    private void removeAttrbute(final HttpSession session, final String attrName) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 删除session中存的验证码
                session.removeAttribute(attrName);
                timer.cancel();
            }
        }, 5 * 60 * 1000);
    }


    /**
     * 检查用户名是否重复
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("checkUsername")
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String msg="";
        if(!userSign.checkUsernameIsExist(username)) {
            msg="username_ok";
            out.print(msg);
        }
        else {
            msg="username_error";
            out.print(msg);
        }
    }

    /**
     * 检查输入的邮箱验证码
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String randomCode = (String) session.getAttribute("randomCode");//session中保存的验证码
        String code = request.getParameter("code");
        String msg = "";
        if(code.equals(randomCode)){
            msg="ok";
            out.print(msg);
        }else {
            msg="error";
            out.print(msg);
        }
    }

    /**
     * 学生用户在线注册
     * @param param 用户输入的值
     * @param response
     * @throws Exception
     */
    @RequestMapping("reg")
    public void reg(String param,HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        Users user = JSON.parseObject(param, new TypeReference<Users>() {});
        LocalDate today = LocalDate.now();
        user.setCreatetime(today.toString());
        user.setState(1);
        user.setType(1);
        Users regUser = userSign.save(user);
        String msg = "";
        if(regUser!=null){
            msg="ok";
            out.print(msg);
        }else{
            msg="error";
            out.print(msg);
        }



    }
}
