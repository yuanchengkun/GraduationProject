package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.Competition;

import java.util.List;

/**
 * 为竞赛表的修改提供服务
 * @author yuanck 201605130
 */
public interface CompetitionService {
    public void save(Competition competition);//保存竞赛信息
    public List<Competition> findAll();//查询所有竞赛
}
