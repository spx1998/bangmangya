package com.xiaoyuanbang.order.domain;

public class OriginRequest extends RequestChao{

    private static final long serialVersionUID = 9097039472451602952L;
    private String fintime; //此处类型可能出错

    public String getFintime() {
        return fintime;
    }

    public void setFintime(String fintime) {
        this.fintime = fintime;
    }
}

