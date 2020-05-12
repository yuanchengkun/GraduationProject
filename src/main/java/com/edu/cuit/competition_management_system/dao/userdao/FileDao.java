package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.FileUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileDao extends JpaRepository<FileUpload,Integer> {
    List<FileUpload> findAllByTeamid(Integer teamid);
    List<FileUpload> findAllByUserid(Integer userid);

    Page<FileUpload> findAllByUserid(Integer userid,Pageable pageable);
    Page<FileUpload> findAllByTeamidAndFilelimit(Integer teamid,int flielimit,Pageable pageable);

    List<FileUpload> findAllByFilelimit(int filelimit);

    @Query(value = "SELECT * from file WHERE teamid in(SELECT teamid from team WHERE comid in(SELECT comid from competition WHERE comtpid =?1))",nativeQuery = true)
    Page<FileUpload> findAllByComtp(int comtpid, Pageable pageable);

}
