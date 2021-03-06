package com.xiaoyuanbang.common.Threads;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xiaoyuanbang.common.domain.APPINFO;
import com.xiaoyuanbang.common.domain.TemplateData;
import com.xiaoyuanbang.common.domain.TokenMsg;
import com.xiaoyuanbang.common.utils.HttpRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TemplateThread implements Runnable {

    private String holder_openid;
    private String worker_name;
    private int reqid;
    private String accessToken;
    private String form_id;

    public TemplateThread(int reqid, String openid, String username, String formId) {
        this.holder_openid = openid;
        this.worker_name = username;
        this.reqid = reqid;
        this.accessToken=getToken();
        this.form_id = formId;
    }

    private String getToken() {
        String params ="grant_type="+ APPINFO.GRANT_TYPE_TEMPLATE+"&appid="+APPINFO.APP_ID+"&secret="+APPINFO.APP_SECRET;
        String result = HttpRequest.sendGet("https://api.weixin.qq.com/cgi-bin/token",params);
        Gson gson =new Gson();
        TokenMsg  tokenMsg= gson.fromJson(result,TokenMsg.class);
        return tokenMsg.getAccess_token();
    }

    @Override
    public void run() {
        Map dataMap = generateJson(reqid,worker_name);
        String urlParam ="access_token="+accessToken;
//        String params = +"&touser="+holder_openid+"&template_id="+APPINFO.TEMPLATE_ID+"&form_id="+form_id+"&data="+mydata;
        Map<String,Object> map=new HashMap<>();
        map.put("touser",holder_openid);
        map.put("template_id",APPINFO.TEMPLATE_ID);
        map.put("form_id",form_id);
        map.put("data",dataMap);
        String body = JSON.toJSONString(map);
        String str =HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?"+urlParam,body);
//        System.out.println(str);
    }

    private Map generateJson(int reqid, String worker_name) {
        TemplateData templateData1 = new TemplateData(reqid);
        TemplateData templateData2 =new TemplateData(worker_name);
//        Gson gson =new Gson();
        ConcurrentHashMap<String,TemplateData> map= new ConcurrentHashMap<>();
        map.put("keyword1",(templateData1));
        map.put("keyword2",(templateData2));
        return map;
    }
}
