package com.xiaoyuanbang.common.domain;

public class TemplateData {
    private String value;
    private String color="#173177";

    public TemplateData(int reqid) {
        this.value = String.valueOf(reqid);
    }

    public TemplateData(String worker_name) {
        this.value =worker_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
