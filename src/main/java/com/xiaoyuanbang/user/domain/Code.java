package com.xiaoyuanbang.user.domain;


import java.io.Serializable;

public class Code implements Serializable {
    private static final long serialVersionUID = 9041399246549383405L;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
