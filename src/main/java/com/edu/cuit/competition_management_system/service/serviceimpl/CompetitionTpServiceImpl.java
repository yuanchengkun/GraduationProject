package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.ComTpDao;
import com.edu.cuit.competition_management_system.dao.userdao.FindUser;
import com.edu.cuit.competition_management_system.entity.Competitiontype;
import com.edu.cuit.competition_management_system.entity.Users;
import com.edu.cuit.competition_management_system.service.ComTpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionTpServiceImpl implements ComTpService {
    @Autowired
    ComTpDao comTpDao;
    @Autowired
    FindUser findUser;
    @Override
    public List<Competitiontype> findAllComTp() {
        return comTpDao.findAll();
    }

    @Override
    public void saveComTp(Competitiontype competitiontype) {
        comTpDao.save(competitiontype);
    }

    @Override
    public Page<Competitiontype> findPagercomTp(int currentPageNumber, int pageSize) {
        Pageable pager = PageRequest.of(currentPageNumber,pageSize);
        return comTpDao.findAll(pager);
    }

    @Override
    public List<Users> findAllTeaWithComTp(int comtpid) {
        return findUser.findAllByComtpid(comtpid);
    }
}
