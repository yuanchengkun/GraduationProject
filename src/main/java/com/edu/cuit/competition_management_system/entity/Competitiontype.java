package com.edu.cuit.competition_management_system.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "competitiontype")
public class Competitiontype {
    @Transient
    final private String[] COM_CLASS ={"第一档次","第二档次","第三档次","第四档次"};
    @Transient
    final private String[] COM_LEVEL ={"国家级学科竞赛","省级学科竞赛","校级学科竞赛"};
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comtpid;//竞赛类型idid
    private String name;//竞赛类型名称
    private String comclass;//竞赛档次

    public String[] getCOM_CLASS() {
        return COM_CLASS;
    }

    public String[] getCOM_LEVEL() {
        return COM_LEVEL;
    }

    public String getComclass() {

        return comclass;
    }

    public void setComclass(String comclass) {
        this.comclass = comclass;
    }

    public String getComlevel() {
        return comlevel;
    }

    public void setComlevel(String comlevel) {
        this.comlevel = comlevel;
    }

    public String getComorganizer() {
        return comorganizer;
    }

    public void setComorganizer(String comorganizer) {
        this.comorganizer = comorganizer;
    }

    private String comlevel;//竞赛级别
    private String comorganizer;//主办单位

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
