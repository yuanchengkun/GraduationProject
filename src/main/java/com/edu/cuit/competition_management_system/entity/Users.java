package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String truename;
    private Integer type;/*0管理员 1 学生 2 老师*/
    private String describe1;
    private String createby;
    private String createtime;
    @Column(columnDefinition = "integer default 1")
    private Integer state;
    private String email;
    private Integer card;
    private Integer teamid;/*学生的团队*/
    private Integer comid;/*学生报名的竞赛*/
    private Integer comtpid;/*老师指导的竞赛类型*/

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", truename='" + truename + '\'' +
                ", type=" + type +
                ", describe1='" + describe1 + '\'' +
                ", createby='" + createby + '\'' +
                ", createtime='" + createtime + '\'' +
                ", state=" + state +
                ", email='" + email + '\'' +
                ", card=" + card +
                ", teamid=" + teamid +
                ", comid=" + comid +
                ", comtpid=" + comtpid +
                ", com=" + com +
                '}';
    }

    public Integer getComtpid() {
        return comtpid;
    }

    public void setComtpid(Integer comtpid) {
        this.comtpid = comtpid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Integer getComid() {
        return comid;
    }

    public void setComid(Integer comid) {
        this.comid = comid;
    }

    public Competition getCom() {
        return com;
    }

    public void setCom(Competition com) {
        this.com = com;
    }

    @Transient

    private Competition com;/*学生参加的竞赛或老师指导的竞赛*/




    public Integer getCard() {
        return card;
    }

    public void setCard(Integer card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescribe1() {
        return describe1;
    }

    public void setDescribe1(String describe) {
        this.describe1 = describe;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
