package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.NoticeDao;
import com.edu.cuit.competition_management_system.entity.Notice;
import com.edu.cuit.competition_management_system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {


    @Autowired
    NoticeDao noticeDao;
    @Override
    public Page<Notice> findAllPageNotice(Example ex, Pageable page) {
        return noticeDao.findAll(ex,page);
    }

    @Override
    public List<Notice> findNoticeInFiveDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();

        c.add(Calendar.DATE, - 5);

        Date time = c.getTime();

        String preDay = sdf.format(time);

        return noticeDao.findBySendtimeAfter(preDay);
    }

    @Transactional
    @Override
    public void saveNotice(Notice notice) {
        noticeDao.save(notice);
    }
    @Override
    public List<Notice> findAllNotice() {
        return noticeDao.findAll();
    }
}
