package com.xiaoyuanbang.order.domain;

import java.io.Serializable;

public  class RequestChao implements Serializable {


    private static final long serialVersionUID = -7972609696831232175L;
     int reqid;
     String name;
     String description;
     String type;
     int price;
     String state;
     String school;
     int holder_id;
     int worker_id;

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
}



