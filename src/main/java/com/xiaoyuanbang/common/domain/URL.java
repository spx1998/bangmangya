package com.xiaoyuanbang.common.domain;

import java.io.Serializable;

public class URL implements Serializable {
    private static final long serialVersionUID = 6034659054466948545L;
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
