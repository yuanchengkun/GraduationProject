package com.edu.cuit.competition_management_system.action;

import com.edu.cuit.competition_management_system.dao.userdao.ComDao;
import com.edu.cuit.competition_management_system.dao.userdao.FileDao;
import com.edu.cuit.competition_management_system.dao.userdao.TeamDao;
import com.edu.cuit.competition_management_system.entity.*;
import com.edu.cuit.competition_management_system.service.ComTpService;
import com.edu.cuit.competition_management_system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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
    @Autowired
    ComTpService comTpService;
    @Autowired
    ComDao comDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    FileDao fileDao;

    /**
     * 初始化首页数据
     * @param session
     * @return
     */
    @RequestMapping("index")
    public String index(HttpSession session){
        LocalDate today = LocalDate.now();
        int page = 0,size = 6;
        Pageable pager = PageRequest.of(page,size);
        Page<Competition> competitions = comDao.findAllByEndtimeIsAfterAndPicIsNotNull(today.toString(),pager);
        List<Competition> competitionList = competitions.getContent();
        Page<Team> teams = teamDao.findAllByStateAndPicNotNull(1,pager);
        List<Team> teamList =teams.getContent();
        List<FileUpload> fileUploads = fileDao.findAllByFilelimit(2);//查询所有公开文档
        session.setAttribute("noticeListInFive",noticeService.findNoticeInFiveDay());//公告数据
        session.setAttribute("comTp",comTpService.findAllComTp());//初始化报名种类列表
        session.setAttribute("competitions",competitionList);//竞赛宣传数据
        session.setAttribute("teams",teamList);//团队宣传宣传数据
        session.setAttribute("files",fileUploads);//初始化公开文档
        return "index";
    }

    /**
     * 分用户跳转到个人界面
     * @param session
     * @return
     */
    @RequestMapping("mine")
    public String mine(HttpSession session){
        Users users = (Users) session.getAttribute("loginUser");
        if(users!=null) {
            if(users.getType()==0)
                return "redirect:/Admin/index";
            if(users.getType()==2)
                return "redirect:/Teacher/index";
            else
                return "redirect:/User/index";
        }else {
            return "redirect:/LoginAction/toLogin";
        }
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
