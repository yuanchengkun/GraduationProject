package com.edu.cuit.competition_management_system.config;

import com.edu.cuit.competition_management_system.dao.userdao.ComDao;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * java定时任务
 * @author yuanck 2016051230
 * 用于每天定时修改已经过了竞赛期限的团队状态，使团队变成过期状态，并使学生团队属性置空
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    @Autowired
    FindUser findUser;
    //3.添加定时任务
    //@Scheduled(cron = "0/10 * * * * ?")
    //或直接指定时间间隔，例如：单位毫秒

    /**
     * 每天检查日期：用当前日期和每个竞赛的开始时间做对比
     * 找出过期的竞赛（竞赛开始时间小于当前时间）
     * 找出过期竞赛的团队，修改其团队状态为过期
     * 同时申请该团队的申请记录为123的申请变更状态为失效，
     * 再修改用户表的team字段和com字段为空
     */
    @Scheduled(fixedRate=1000 *60 *60*24)
    private void configureTasks() {
        LocalDate today = LocalDate.now();
        System.out.println("开始执行"+today.toString());
        findUser.checkcom(today.toString());
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}