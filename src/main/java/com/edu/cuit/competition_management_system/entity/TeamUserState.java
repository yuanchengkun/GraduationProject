package com.edu.cuit.competition_management_system.entity;

public enum TeamUserState {
    //组团状态数据库中字段 成为团员0 申请中1 取消申请2  拒绝申请3 团队过期4
    Chengweituanyuan(1, "成为团员"), Shengqingzhong(2, "申请中"),Quxiaoshengqing(3,"取消申请"),Jujueshengqing(4,"拒绝申请"),Tuanduishixiao(5,"团队失效");
    private int key;
    private String value;

    private TeamUserState (int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TeamUserState{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
