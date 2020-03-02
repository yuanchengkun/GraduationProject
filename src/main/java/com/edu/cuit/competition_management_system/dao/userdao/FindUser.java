package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindUser extends JpaRepository <Users,Integer>{
    Users findByUsernameAndPassword(String username,String password);

    Users findByUsername(String username);
}
