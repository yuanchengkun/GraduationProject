package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.Competitiontype;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComTpDao extends JpaRepository<Competitiontype,Integer> {

}
