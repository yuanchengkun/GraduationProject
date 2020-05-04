package com.edu.cuit.competition_management_system.dao.userdao;

import com.edu.cuit.competition_management_system.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FindUser extends JpaRepository <Users,Integer>{
    Users findByUsernameAndPasswordAndState(String username,String password,Integer state);

    Users findByUsername(String username);

    List<Users> findByTypeIsAndStateIs(Integer integer,Integer state);
    List<Users> findAllByStateIs(Integer state);
    @Modifying
    @Query("update Users u set u.state = ?1 where u.id = ?2")
    void setUserDelete(int state, int id); //通过用户id修改用户状态

    @Modifying
    @Query("update Users u set u.password = ?2 where u.id = ?1")
    void setUserPass(int id, String psw); //通过用户id修改用户密码
    @Modifying
    @Query("update Users u set u.comid = ?2 where u.id = ?1")
    void setUserCom(int id, int comid); //通过用户id修改用户选择的竞赛id
    @Modifying
    @Query("update Users u set u.comtpid = ?2 where u.id = ?1")
    void setUserComtp(int id, Integer comtpid); //通过用户id修改用户选择的竞赛id

    List<Users> findAllByComtpid(int comtpid);//根据指导的竞赛类型查找老师
    List<Users> findAllByComidNotNull();//查询所有报名了的学生

    Page<Users> findAllByComidNotNullAndType(int type,Pageable pageable);
}
