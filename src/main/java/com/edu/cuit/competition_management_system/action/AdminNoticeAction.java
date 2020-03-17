package com.edu.cuit.competition_management_system.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.cuit.competition_management_system.entity.Competitiontype;
import com.edu.cuit.competition_management_system.entity.Notice;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.json.LayuiTable;
import com.edu.cuit.competition_management_system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

/**
 * @author yuanck 2016051230
 * 描述：公告管理控制器
 */
@Controller
@RequestMapping("AdminNotice")
public class AdminNoticeAction {
    @Autowired
    NoticeService noticeService;
    @RequestMapping("toNotice")
    public String toNotice(){
        return "admin/notice/notice_list";
    }

    /**
     * 分页查询所有通知
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping("findNoticeAll")
    public LayuiTable findNoticeAll(Integer type,Integer limit,Integer page){
        LayuiTable layuiTable = new LayuiTable();
        Notice notice = new Notice();
        notice.setType(type);
        ExampleMatcher matcher= ExampleMatcher.matching();
        Example<Notice> example = Example.of(notice,matcher);
        Pageable pageable = PageRequest.of(page-1,limit);
        Page<Notice> pageList = noticeService.findAllPageNotice(example,pageable);
        List<Notice> noticeList = pageList.getContent();
        layuiTable.setCode(0);
        layuiTable.setMsg("ok");
        layuiTable.setCount(pageList.getTotalElements());
        layuiTable.setData(noticeList);
        return layuiTable;
    }
    @RequestMapping("noticeAdd")
    public String noticeAdd(){
        return "admin/notice/notice_add";
    }
    /**
     * 添加公告
     * @param param
     * @param response
     * @throws IOException
     */
    @RequestMapping("addNotice")
    public void updatetp(String param,HttpServletResponse response,HttpSession session)throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String msg="";
        Notice notice = JSON.parseObject(param, new TypeReference<Notice>() {});
        Users users = (Users) session.getAttribute("loginUser");
        LocalDate today = LocalDate.now();
        notice.setSendtime(today.toString());
        notice.setUsername(users.getTruename());
        notice.setType(0);
        try {
            //comTpService.saveComTp(competitiontype);
            noticeService.saveNotice(notice);
            msg="ok";
            out.print(msg);
        } catch (Exception e) {
            msg="error1";
            out.print(msg);
        }
    }
}
