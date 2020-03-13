package com.edu.cuit.competition_management_system.service;

import com.edu.cuit.competition_management_system.entity.Competitiontype;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 为竞赛类型表的修改提供服务
 * @author yuanck 201605130
 */
public interface ComTpService {
    public List<Competitiontype> findAllComTp();//查询所有竞赛类型
    public void saveComTp(Competitiontype competitiontype);//保存竞赛类型
    Page<Competitiontype> findPagercomTp(int currentPageNumber, int pageSize);//查询分页竞赛种类
}
