package com.edu.cuit.competition_management_system.service.serviceimpl;

import com.edu.cuit.competition_management_system.dao.userdao.ComDao;
import com.edu.cuit.competition_management_system.dao.userdao.ComTpDao;
import com.edu.cuit.competition_management_system.entity.Competition;
import com.edu.cuit.competition_management_system.entity.Competitiontype;
import com.edu.cuit.competition_management_system.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    ComDao comDao;
    @Autowired
    ComTpDao comTpDao;

    @Transactional
    @Override
    public void save(Competition competition) {
        comDao.save(competition);
    }

    @Override
    public List<Competition> findAll() {
        List<Competition> competitions = comDao.findAll();
        Iterator<Competition> it = competitions.iterator();
        int n;
        Competition competition;
        while (it.hasNext()){
            competition = (Competition)it.next();
            if(competition.getComtpid()!=null){
                n = competition.getComtpid();
                competition.setCompetitiontype(comTpDao.findById(n).get());
            }
        }
        return competitions;
    }

    @Override
    public Page<Competition> findPagercom(int currentPageNumber, int pageSize) {
        Pageable pager = PageRequest.of(currentPageNumber,pageSize);
        return comDao.findAll(pager);
    }
}
