package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.ComDao;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.entity.Competition;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.UserSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserSignImpl implements UserSign {
    @Autowired
    FindUser findUser;
    @Autowired
    ComDao comDao;
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

    @Override
    public List<Users> findAllByType(int i) {
       /* List<Users> a = new ArrayList<Users>();
        a=findUser.findByTypeIsAndStateIs(i,1);
        Iterator<Users> it=a.iterator();
        int n;
        while(it.hasNext()) {
            Users nr=(Users) it.next();
            if(nr.getComid()!=null) {
                n = nr.getComid();
                Competition competition = comDao.findById(n).get();
                nr.setCom(competition);
            }

        }*/
        return findUser.findByTypeIsAndStateIs(i,1);
    }
    @Transactional
    @Override
    public void setUserState(int id,int state) {
        findUser.setUserDelete(state,id);
    }

    @Override
    public List<Users> findAllDelUser() {
        return findUser.findAllByStateIs(0);
    }
    @Transactional
    @Override
    public void setUserPass(int id, String pass) {
        findUser.setUserPass(id,pass);
    }

    @Transactional
    @Override
    public void setUserCom(int id, int comid) {
        findUser.setUserCom(id,comid);
    }
}
