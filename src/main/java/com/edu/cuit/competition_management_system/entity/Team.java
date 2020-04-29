package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamid;//团队id

    //private Integer comid;//竞赛id
    @JoinColumn(name = "comid",insertable = false, updatable = false)
    @ManyToOne
    private Competition competition;//竞赛对象

    private Integer comid;

    //private Integer teaid;//指导老师id
    @JoinColumn(name = "teaid",insertable = false, updatable = false)
    @ManyToOne
    private Users teacher;//指导老师对象

    private Integer teaid;

    @Override
    public String toString() {
        return "Team{" +
                "teamid=" + teamid +
                ", competition=" + competition +
                ", comid=" + comid +
                ", teacher=" + teacher +
                ", teaid=" + teaid +
                ", captainid=" + captainid +
                ", captain=" + captain +
                ", info='" + info + '\'' +
                ", teamname='" + teamname + '\'' +
                ", state=" + state +
                '}';
    }

    public Integer getComid() {
        return comid;
    }

    public void setComid(Integer comid) {
        this.comid = comid;
    }

    public Integer getTeaid() {
        return teaid;
    }

    public void setTeaid(Integer teaid) {
        this.teaid = teaid;
    }

    public Integer getCaptainid() {
        return captainid;
    }

    public void setCaptainid(Integer captainid) {
        this.captainid = captainid;
    }

    private Integer captainid;//队长id

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Users getTeacher() {
        return teacher;
    }

    public void setTeacher(Users teacher) {
        this.teacher = teacher;
    }

    public Users getCaptain() {
        return captain;
    }

    public void setCaptain(Users captain) {
        this.captain = captain;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @JoinColumn(name = "captainid",insertable = false, updatable = false)
    @ManyToOne
    private Users captain;//队长对象

    private String info;//团队介绍
    private String teamname;//团队名字
    @Column(name = "state", columnDefinition = "int", insertable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private TeamState teamState;
    private Integer state;//团队状态


}
