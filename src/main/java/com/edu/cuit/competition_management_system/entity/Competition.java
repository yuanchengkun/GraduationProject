package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comid;//竞赛id
    private Integer comtpid;//竞赛种类id
    private String starttime;//竞赛报名开始时间
    private String endtime;//竞赛报名结束时间
    private String comtime;//竞赛开始时间
    private String miaosu;//竞赛描述
    @Transient
    private Competitiontype competitiontype;//内置竞赛种类对象

    public Competitiontype getCompetitiontype() {
        return competitiontype;
    }

    public void setCompetitiontype(Competitiontype competitiontype) {
        this.competitiontype = competitiontype;
    }

    public Integer getComid() {
        return comid;
    }

    public void setComid(Integer comid) {
        this.comid = comid;
    }

    public Integer getComtpid() {
        return comtpid;
    }

    public void setComtpid(Integer comtpid) {
        this.comtpid = comtpid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getComtime() {
        return comtime;
    }

    public void setComtime(String comtime) {
        this.comtime = comtime;
    }

    public String getMiaosu() {
        return miaosu;
    }

    public void setMiaosu(String miaosu) {
        this.miaosu = miaosu;
    }
}
