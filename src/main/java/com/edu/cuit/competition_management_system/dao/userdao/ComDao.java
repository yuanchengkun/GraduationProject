package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComDao extends JpaRepository<Competition,Integer> {
}
