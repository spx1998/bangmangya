package com.xiaoyuanbang.order.controller;

import com.google.gson.Gson;
import com.xiaoyuanbang.common.utils.AESUtil;
import com.xiaoyuanbang.order.dao.OrderDao;
import com.xiaoyuanbang.order.domain.OriginRequest;
import com.xiaoyuanbang.order.domain.REQUEST_CONSTANT;
import com.xiaoyuanbang.order.domain.RequestInfo;
import com.xiaoyuanbang.user.dao.UserDao;
import com.xiaoyuanbang.user.domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Service
@RestController
public class OrderController {

    @Autowired
    OrderDao orderDao ;

    @Autowired
    UserDao userDao;

    private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private Gson g= new Gson();

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
                                  @RequestParam("reqid")int reqid){
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
                                 @RequestParam("reqid") int reqid){
        HashMap<String,String> infoMap;
        try{
            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            User user=userDao.getUser(openid);
            int userid;
            if(StringUtils.isNotBlank(user.getWxid())||user.getQqid()!=0||user.getPhone()!=0){
                userid = user.getId();
            }else{
                return "no contact";
            }
            if(0==orderDao.setRequestConfirm(reqid,userid, REQUEST_CONSTANT.STATE_ACCEPT,REQUEST_CONSTANT.STATE_CREATE)){
                return "can't confirm, maybe has been confirmed already";
            }
            int holder_id =orderDao.getHolderId(reqid);
            User holder = userDao.getUserById(holder_id);

            //返回联系方式map
            infoMap = new HashMap<>();
            infoMap.put("username",holder.getUsername());
            infoMap.put("qqid", String.valueOf(holder.getQqid()));
            infoMap.put("wxid",holder.getWxid());
            infoMap.put("phone",String.valueOf(holder.getPhone()));


        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return g.toJson(infoMap);
    }
    /**
     * 发布订单
     */
    @Transactional
    @PostMapping("/record/create")
    public String createRequest(@RequestHeader("mySession")String mySession,@RequestBody String requestInfoJson) {
        try {
            //处理fintime日期的问题
            OriginRequest originRequest= g.fromJson(requestInfoJson, OriginRequest.class);
            Date fintime = Date.valueOf(originRequest.getFintime());
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setFintime(fintime);
            requestInfo.setName(originRequest.getName());
            requestInfo.setType(originRequest.getType());
            requestInfo.setSchool(originRequest.getSchool());
            requestInfo.setPrice(originRequest.getPrice());
            requestInfo.setDescription(originRequest.getDescription());
                    //new RequestInfo(originRequest.getName(),originRequest.getDescription(),originRequest.getPrice(),originRequest.getSchool(),originRequest.getType(),fintime);
            requestInfo.setFintime(fintime);

            String openid = AESUtil.decrypt(mySession, AESUtil.KEY);
            User user=userDao.getUser(openid);
            if(StringUtils.isBlank(user.getWxid())&&user.getQqid()==0&&user.getPhone()==0){
                return "no contact";
            }
            int userid=user.getId();

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
    /**
     *确认订单已完成
     */
    @Transactional
    @PostMapping("/record/finish")
    public String finishRequest(@RequestHeader("mySession")String mySession,@RequestParam("reqid") int reqid){
        try {
            String openid =AESUtil.decrypt(mySession,AESUtil.KEY);
            if(0==orderDao.setRequestState(reqid,REQUEST_CONSTANT.STATE_FINISH,REQUEST_CONSTANT.STATE_ACCEPT)){
                return "can't change";
            }


        }catch (Exception e){
            return "error";
        }
        return "ok";
    }

    /**
     * 取消订单
     */
    @Transactional
    @PostMapping("/record/cancel")
    public String cancelRequest(@RequestHeader("mySession")String mySession,@RequestParam("reqid") int reqid){
        try {
            String openid =AESUtil.decrypt(mySession,AESUtil.KEY);
            if(0==orderDao.setRequestState(reqid,REQUEST_CONSTANT.STATE_CANCEL,REQUEST_CONSTANT.STATE_CREATE)){
                return "can't change";
            }


        }catch (Exception e){
            return "error";
        }
        return "ok";
    }

}
