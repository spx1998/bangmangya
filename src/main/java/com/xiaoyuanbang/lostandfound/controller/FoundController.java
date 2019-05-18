package com.xiaoyuanbang.lostandfound.controller;

import com.google.gson.Gson;
import com.xiaoyuanbang.common.utils.AESUtil;
import com.xiaoyuanbang.common.utils.FileUtils;
import com.xiaoyuanbang.lostandfound.dao.LostInfoDao;
import com.xiaoyuanbang.lostandfound.domain.LOST_CONSTANTS;
import com.xiaoyuanbang.lostandfound.domain.LostInfo;
import com.xiaoyuanbang.user.dao.UserDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
@RestController
public class FoundController {
    @Value("${web.upload-path}")
    String path;

    @Autowired
    LostInfoDao lostInfoDao;

    @Autowired
    UserDao userDao;

    private Gson g=new Gson();

    /**
     *上传图片
     */
    @Transactional
    @PostMapping("/found/upload")
    public String uploadPicture(@RequestHeader("mySession")String mySession, @RequestParam(value = "file", required = false) MultipartFile picture){
        String pictureName;
        try {
            //生成文件名,originalFileName为文件名
            String extendedName = Objects.requireNonNull(picture.getOriginalFilename()).substring(picture.getOriginalFilename().lastIndexOf("."));
            String openid = AESUtil.decrypt(mySession,AESUtil.KEY);
            pictureName =openid+"-"+System.currentTimeMillis()+extendedName;
            //存储
            boolean uploadSuccessful=FileUtils.upload(picture, path, pictureName);
            //返回信息，返回路径
            if(!uploadSuccessful){
                return "upload error";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return path+"/"+pictureName;
    }
    /**
     * 取消上传
     */
    @Transactional
    @PostMapping("/found/upload/cancel")
    public String cancelUpload(@RequestHeader String mySession,@RequestParam("url")String url){
        try{
            //提取文件名
            String fileName = StringUtils.substringAfter(url,"/picture/");
            File file = new File(url);
            //删除
            if(file.isFile()){
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }

    /**
     * 发布寻物/寻找失主信息
     */
    @Transactional
    @PostMapping("/found/create")
    public String createLostInfo(@RequestHeader String mySession, @RequestBody LostInfo lostInfo){
        try{
            String openid= AESUtil.decrypt(mySession,AESUtil.KEY);
            int holder_id = userDao.getId(openid);
            lostInfo.setHolder_id(holder_id);
            lostInfoDao.addRecord(lostInfo);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }

    /**
     * 展示信息
     */
    @Transactional
    @GetMapping("/found/list")
    public String getLostInfo(@RequestHeader String mySession, @RequestParam("school")String school,@RequestParam("type")int type){
        List<LostInfo> lostInfos;
        try{
            lostInfos =lostInfoDao.getLostInfo(school,type);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return g.toJson(lostInfos);
    }

    /**
     * 查看详情
     */
    @Transactional
    @GetMapping("/found/detail/{lostid}")
    public String getRecordDetail(@RequestHeader String mySession,@PathVariable("lostid")int id ){
        LostInfo lostInfo;
        try{
            lostInfo = lostInfoDao.getRecordDetail(id);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return g.toJson(lostInfo);
    }

    /**
     * 确认信息已失效
     */
    @Transactional
    @PostMapping("/found/finish")
    public String finishLostRecord(@RequestHeader String mySession,@RequestParam("lostid") int id){
        try{
            lostInfoDao.setRecordFinish(id, LOST_CONSTANTS.STATE_FINISH);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "ok";
    }


}
