package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignImpl implements UserSign {
    @Autowired
    FindUser findUser;
    @Override
    public Users userSign(String username, String password) {
        return findUser.findByUsernameAndPassword(username,password);
    }
}
