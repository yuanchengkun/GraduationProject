package com.edu.cuit.competition_management_system.entity;

public enum TeamState {
    //团队状态数据库中字段 失效0 有效1 申请创建中2  申请失败3
    团队过期(1, "团队过期"), 有效团队(2, "有效团队"),申请创建中(3,"申请创建中"),申请失败(4,"申请失败");
    private int key;
    private String value;

    private TeamState (int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
