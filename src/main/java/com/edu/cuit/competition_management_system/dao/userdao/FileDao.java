package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDao extends JpaRepository<FileUpload,Integer> {
    List<FileUpload> findAllByTeamid(Integer teamid);
    List<FileUpload> findAllByUserid(Integer userid);
}
