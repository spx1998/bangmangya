package com.xiaoyuanbang.user.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xiaoyuanbang.common.domain.APPINFO;
import com.xiaoyuanbang.common.domain.MsgResult;
import com.xiaoyuanbang.common.utils.AESUtil;
import com.xiaoyuanbang.common.utils.HttpRequest;
import com.xiaoyuanbang.user.dao.UserDao;
import com.xiaoyuanbang.user.domain.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * 控制用户登录、注册、绑定学校等相关操作
 * @author spx
 */

@Service
@RestController
public class UserController {
    //需要激活日志

    private Gson g = new Gson();

    @Autowired
    UserDao userDao;

    @Transactional
    @RequestMapping("/user/login")
    public String Login(@RequestBody String codeJson) {
        String userInfoJson;
        Code code=g.fromJson(codeJson,Code.class);
        String params = "appid=" + APPINFO.APP_ID + "&secret=" + APPINFO.APP_SECRET + "&js_code=" + code.getCode() + "&grant_type="+ APPINFO.GRANT_TYPE;


            userInfoJson = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        UserInfo userInfo = g.fromJson(userInfoJson, UserInfo.class);

        if(StringUtils.isBlank(userInfo.getOpenid())){
            return "wrong: "+userInfo.getErrcode();
        }
        
        //若数据库中无该openid，则新建用户，否则，返回用户数据。
        if (StringUtils.isBlank(userDao.hasThisUser(userInfo.getOpenid()))) {

            userDao.createNewUser(userInfo.getOpenid());
        }
        /**
         *学校的绑定逻辑不应该在此方法，应该在获取用户名和头像的操作之后
         */
        User user = userDao.getUser(userInfo.getOpenid());
        user.setMySession(AESUtil.encrypt(userInfo.getOpenid(),AESUtil.KEY));
        return g.toJson(user) ;

    }

    @Transactional
    @PostMapping("/user/signup")
    public String signUp(@RequestHeader("mySession")String mysession, @RequestParam("name") String name, @RequestParam("gender") String gender){

        try{
            String openid=AESUtil.decrypt(mysession,AESUtil.KEY);
            userDao.updateUserByOpenid(openid,name,gender, USER_CONSTANTS.STATE_SIGNUP);//还需要改变状态！
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }
    @Transactional
    @PostMapping("/user/bind")
    public String bingSchool(@RequestHeader("mySession")String mysession,@RequestParam("school") String school){

        try{
            String openid=AESUtil.decrypt(mysession,AESUtil.KEY);
            userDao.bindSchool(openid,school,USER_CONSTANTS.STATE_BIND);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }

    @Transactional
    @PostMapping("/user/contact")
    public String bindUserContact(@RequestHeader("mySession")String mysession,
                                  @RequestParam("qqid")String qqid,
                                  @RequestParam("wxid")String wxid,
                                  @RequestParam("phone")String phone){
        try{
            String openid=AESUtil.decrypt(mysession,AESUtil.KEY);
            userDao.bindUserContact(openid,qqid,wxid,phone,USER_CONSTANTS.STATE_CONTACT); //需要修改状态
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }

    /**
     *  拉取学校列表
     */

    @Transactional
    @GetMapping("/school")
    public List<String> getSchool(){
        return userDao.getSchoolList();
    }
}
