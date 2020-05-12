package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileid;
    private String filename;
    private String savename;
    private String uploaddate;

    @JoinColumn(name = "userid",insertable = false, updatable = false)
    @ManyToOne
    private Users users;//竞赛对象
    private Integer userid;

    @JoinColumn(name = "teamid",insertable = false, updatable = false)
    @ManyToOne
    private Team team;
    private Integer teamid;//共享的小组id

    @Column(insertable = false)
    private Integer filelimit;//文档公开权限 0个人文档 1组内公开 2公开文档

    public Integer getFilelimit() {
        return filelimit;
    }

    public void setFilelimit(Integer filelimit) {
        this.filelimit = filelimit;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSavename() {
        return savename;
    }

    public void setSavename(String savename) {
        this.savename = savename;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }
}
