package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.entity.Notice;
import com.edu.cuit.competition_management_system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author yuanck 2016051230
 * 描述：项目启动的基本控制器
 */
@Controller
@RequestMapping("BaseAction")
public class BaseAction {
    @Autowired
    NoticeService noticeService;

    @RequestMapping("index")
    public String index(HttpSession session){
        session.setAttribute("noticeListInFive",noticeService.findNoticeInFiveDay());
        return "index";
    }

    @RequestMapping("daohang")
    public String execute(){

        return "daohang";
    }

    /**
     * 跳转到通知详情页面
     * @param id 通知id
     * @param request
     * @return
     */
    @RequestMapping("nDetail")
    public String nDetail(Integer id,HttpServletRequest request){
        List<Notice> noticeList = noticeService.findAllNotice();
        List<Notice> notices = new ArrayList<>();
        Optional<Notice> noticeOptional;

        noticeOptional = noticeList.stream().filter(item->item.getNotid()==id).findFirst();
        Notice notice = noticeOptional.get();
        request.setAttribute("Notice",notice);//添加显示新闻

        noticeOptional = noticeList.stream().filter(item->item.getNotid()>id).findFirst();
        if(noticeOptional.isPresent())
            request.setAttribute("NoticeAfter",noticeOptional.get());;//添加下一条新闻

        noticeOptional = noticeList.stream().filter(item->item.getNotid()<id).findFirst();
        if(noticeOptional.isPresent())
            request.setAttribute("NoticeBefore",noticeOptional.get());//添加上一条新闻

        return "main/nDetail";
    }

    /**
     * 跳转到更多新闻页面
     * @param session
     * @return
     */
    @RequestMapping("notice")
    public String notice(HttpSession session){
        session.setAttribute("noticeList",noticeService.findAllNotice());
        return "main/notice";
    }

}
