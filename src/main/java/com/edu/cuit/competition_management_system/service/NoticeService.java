package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.Notice;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    Page<Notice> findAllPageNotice(Example ex, Pageable page);//分页查询所有通知
    void saveNotice(Notice notice);//保存通知
    List<Notice> findNoticeInFiveDay();//查询发布时间在5天内的通知
    List<Notice> findAllNotice();//查询所有的通知
}
