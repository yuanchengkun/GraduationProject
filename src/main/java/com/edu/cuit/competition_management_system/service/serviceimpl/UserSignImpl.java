package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserSignImpl implements UserSign {
    @Autowired
    FindUser findUser;
    @Override
    public Users userSign(String username, String password) {
        return findUser.findByUsernameAndPassword(username,password);
    }

    @Override
    public boolean checkUsernameIsExist(String username) {
        if(findUser.findByUsername(username)!=null)
            return true;
        else return false;
    }
    @Transactional
    @Override
    public Users save(Users user) throws Exception {
        return findUser.save(user);
    }
}
