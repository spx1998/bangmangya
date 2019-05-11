package com.xiaoyuanbang.common.domain;

import java.io.Serializable;

public class MsgResult implements Serializable {

    private static final long serialVersionUID = -2500206433094798920L;
    private String status;
    private String content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
