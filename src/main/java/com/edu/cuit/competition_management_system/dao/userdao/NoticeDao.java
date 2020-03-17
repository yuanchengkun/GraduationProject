package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeDao extends JpaRepository<Notice,Integer> {
    List<Notice> findBySendtimeAfter(String day);
}
