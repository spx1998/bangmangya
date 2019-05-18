package com.xiaoyuanbang.lostandfound.domain;


import java.io.Serializable;

public class LostInfo implements Serializable {

    private static final long serialVersionUID = 2453319546915758392L;
    private int lostid;
    private String name;
    private String description;
    private String school;
    private String type;
    private int holder_id;
    private String state;
    private String urls;

    public int getLostid() {
        return lostid;
    }

    public void setLostid(int lostid) {
        this.lostid = lostid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHolder_id() {
        return holder_id;
    }

    public void setHolder_id(int hold_id) {
        this.holder_id = hold_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}
