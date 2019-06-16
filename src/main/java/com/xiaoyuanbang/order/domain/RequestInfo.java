package com.xiaoyuanbang.order.domain;

import java.io.Serializable;
import java.sql.Date;

public class RequestInfo   implements Serializable {

    private static final long serialVersionUID = 8769242197198044722L;

    private Date fintime; //此处类型可能出错
    private int reqid;
    private String name;
    private String description;
    private String type;
    private int price;
    private String state;
    private String school;
    private int holder_id;
    private int worker_id;
    private String formId;
    private double score;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public int getReqid() {
        return reqid;
    }

    public void setReqid(int reqid) {
        this.reqid = reqid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getHolder_id() {
        return holder_id;
    }

    public void setHolder_id(int holder_id) {
        this.holder_id = holder_id;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int work_id) {
        this.worker_id = work_id;
    }


    public Date getFintime() {
        return fintime;
    }

    public void setFintime(Date fintime) {
        this.fintime = fintime;
    }
}
