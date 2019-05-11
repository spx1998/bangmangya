package com.xiaoyuanbang.order.controller;

import com.google.gson.Gson;
import com.xiaoyuanbang.common.utils.AESUtil;
import com.xiaoyuanbang.order.dao.OrderDao;
import com.xiaoyuanbang.order.domain.REQUEST_CONSTANT;
import com.xiaoyuanbang.order.domain.RequestInfo;
import com.xiaoyuanbang.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
public class OrderController {

    @Autowired
    OrderDao orderDao ;

    @Autowired
    UserDao userDao;

    Gson g= new Gson();

    /**
     * 拉取主界面
     */
    @Transactional
    @GetMapping("/home/list")
    public String getRequestList(@RequestHeader("mySession") String mySession){
        try{
            String openid = AESUtil.decrypt(mySession,AESUtil.KEY);
            String school=userDao.getSchoolByOpenid(openid);
            List<RequestInfo> requestInfos=orderDao.getRequestList(school,REQUEST_CONSTANT.STATE_CREATE);
            return g.toJson(requestInfos);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }
    /**
     *  获取分类列表
     */
    @Transactional
    @GetMapping("/home/list/{type}")
    public String getRequestListByType(@RequestHeader("mySession") String mySession,
                                       @PathVariable("type") String type){
        try {
            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            String school = userDao.getSchoolByOpenid(openid);
            List<RequestInfo> requestInfos = orderDao.getRequestListByType(school, type,REQUEST_CONSTANT.STATE_CREATE);
            return g.toJson(requestInfos);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    /**
     * 获取需求详情
     */
    @Transactional
    @GetMapping("/record/detail")
    public String getRecordDetail(@RequestHeader("mySession")String mySession,
                                  @RequestParam("reqid")String reqid){
        try {
            //String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            //String school = userDao.getSchoolByOpenid(openid);

            RequestInfo requestInfo= orderDao.getRequest(reqid);
            return g.toJson(requestInfo);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * worker 接单
     */
    @Transactional
    @PostMapping("/record/confirm")
    public String confirmRequest(@RequestHeader("mySession")String mySession,
                                 @RequestParam("reqid") String reqid){
        try{
            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);

            int userid = userDao.getId(openid);
            orderDao.setRequestConfirm(reqid,userid, REQUEST_CONSTANT.STATE_ACCEPT);

            /**
             * 还需要返回holder的联系方式 还没有写
             */


        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }
    /**
     * 发布订单
     */
    @Transactional
    @PostMapping("/record/create")
    public String createRequest(@RequestHeader("mySession")String mySession,@RequestBody String requestInfoJson) {
        try {
            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            RequestInfo requestInfo = g.fromJson(requestInfoJson, RequestInfo.class);
            int userid = userDao.getId(openid);
            orderDao.createRequest(requestInfo.getName(), requestInfo.getDescription(), requestInfo.getFintime(), requestInfo.getSchool(), requestInfo.getType(), requestInfo.getPrice(), userid);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     *获取自己发布的订单
     */
    @Transactional
    @GetMapping("/record/holder/list")
    public String getRequestAsHolder(@RequestHeader String mySession) {
        try{
            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            int userid= userDao.getId(openid);
            List<RequestInfo> requestInfos=orderDao.getRequestAsHolder(userid);
            return g.toJson(requestInfos);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    @Transactional
    @GetMapping("/record/worker/list")
    public String getRequestAsWorker(@RequestHeader String mySession) {
        try{
            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            int userid= userDao.getId(openid);
            List<RequestInfo> requestInfos=orderDao.getRequestAsWorker(userid);
            return g.toJson(requestInfos);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

    }

}
