package com.xiaoyuanbang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class XiaoyuanbangApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuanbangApplication.class, args);
    }

}
