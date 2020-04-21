package com.edu.cuit.competition_management_system.entity;

import javax.persistence.*;

@Entity
@Table(name = "teamuser")
public class TeamUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//组团id

    @JoinColumn(name = "userid", insertable = false, updatable = false)
    @ManyToOne
    private Users student;//团队学生
    private Integer userid;//团队学生id

    @JoinColumn(name = "teamid", insertable = false, updatable = false)
    @ManyToOne
    private Team team;//团队
    private Integer teamid;//团队id

    @Column(name = "state", columnDefinition = "int", insertable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private TeamUserState teamUserState;//组团状态
    private Integer state;//组团状态 成为团员1 申请中2 取消申请3  拒绝申请4 团队过期5


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }


    @Override
    public String toString() {
        return "TeamUser{" +
                "id=" + id +
                ", student=" + student +
                ", userid=" + userid +
                ", team=" + team +
                ", teamid=" + teamid +
                ", teamUserState=" + teamUserState +
                ", state=" + state +
                '}';
    }

    public TeamUserState getTeamUserState() {
        return teamUserState;
    }

    public void setTeamUserState(TeamUserState teamUserState) {
        this.teamUserState = teamUserState;
    }



    public Users getStudent() {
        return student;
    }

    public void setStudent(Users student) {
        this.student = student;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
