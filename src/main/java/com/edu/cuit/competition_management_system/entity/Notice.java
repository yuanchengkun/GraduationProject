package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notid;
    private Integer type;//消息种类 0:通知
    private String title;//消息标题
    private String content;//消息内容
    private String sendtime;//发送时间
    private String username;//发布人姓名

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNotid() {
        return notid;
    }

    public void setNotid(Integer notid) {
        this.notid = notid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}
