package com.xiaoyuanbang.lostandfound.controller;

import com.xiaoyuanbang.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

@Service
@RestController
public class FoundController {
    @Value("${web.upload-path}")
    String path;



    @Transactional
    @PostMapping("/upload")
    public String uploadPicture(@RequestHeader("mySession")String mySession, @RequestParam(value = "file", required = false) MultipartFile picture){
        String pictureName;
        try {
            //生成文件名,originalFileName为文件名
            String extendedName = Objects.requireNonNull(picture.getOriginalFilename()).substring(picture.getOriginalFilename().lastIndexOf("."));
            pictureName =mySession+"-"+System.currentTimeMillis()+extendedName;
            //存储
            boolean uploadSuccessful=FileUtils.upload(picture, path, pictureName);
            //返回信息，返回路径
            if(!uploadSuccessful){
                return "upload error";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }

        return path+pictureName;
    }
}
