package com.xiaoyuanbang.user.domain;

import java.io.Serializable;

public class SafeUser implements Serializable {
    private static final long serialVersionUID = 3461826463872116664L;

    private String username;
    private String gender;
    private String school;
    private String mySession;
    private String state;


    public SafeUser(String username, String gender, String school, String decrypt, String state) {
        this.username=username;
        this.gender=gender;
        this.school=school;
        this.mySession=decrypt;
        this.state=state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMySession() {
        return mySession;
    }

    public void setMySession(String mySession) {
        this.mySession = mySession;
    }
}
