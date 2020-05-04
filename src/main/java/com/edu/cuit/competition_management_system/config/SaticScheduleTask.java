package com.edu.cuit.competition_management_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * java定时任务
 * @author yuanck 2016051230
 * 用于每天定时修改已经过了竞赛期限的团队状态，使团队变成过期状态，并使学生团队属性置空
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    //3.添加定时任务
    //@Scheduled(cron = "0/10 * * * * ?")
    //或直接指定时间间隔，例如：单位毫秒
    @Scheduled(fixedRate=1000 *60 *60*24)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}