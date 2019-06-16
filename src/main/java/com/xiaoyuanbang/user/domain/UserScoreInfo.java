package com.xiaoyuanbang.user.domain;

import java.io.Serializable;
import java.sql.Date;

public class UserScoreInfo implements Serializable {

    private static final long serialVersionUID = -7861739357490804697L;
    private int id;
    private double score;
    private int requestNum;
    private int lostNum;
    private int acceptNum;
    private int registerReward;
    private String openid;
    private Date registerDate;

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRequestNum() {
        return requestNum;
    }

    public void setRequestNum(int requestNum) {
        this.requestNum = requestNum;
    }

    public int getLostNum() {
        return lostNum;
    }

    public void setLostNum(int lostNum) {
        this.lostNum = lostNum;
    }

    public int getAcceptNum() {
        return acceptNum;
    }

    public void setAcceptNum(int acceptNum) {
        this.acceptNum = acceptNum;
    }

    public int getRegisterReward() {
        return registerReward;
    }

    public void setRegisterReward(int registerReward) {
        this.registerReward = registerReward;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
