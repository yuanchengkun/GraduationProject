package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "competitiontype")
public class Competitiontype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comtpid;//竞赛类型idid
    private String name;//竞赛类型名称

    public Integer getComtpid() {
        return comtpid;
    }

    public void setComtpid(Integer comtpid) {
        this.comtpid = comtpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
