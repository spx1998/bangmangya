package com.xiaoyuanbang.user.domain;

import java.io.Serializable;

public class SafeUser implements Serializable {
    private static final long serialVersionUID = 3461826463872116664L;

    private String username;
    private String wxid;
    private int qqid;
    private int phone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public int getQqid() {
        return qqid;
    }

    public void setQqid(int qqid) {
        this.qqid = qqid;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
