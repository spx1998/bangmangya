package com.xiaoyuanbang.user.dao;

import com.xiaoyuanbang.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserDao {

    String hasThisUser(String openid);

    void createNewUser(String userInfo);

    User getUser(String openid);

    void updateUserByOpenid(@Param("openid") String openid, @Param("name") String name, @Param("gender") String gender, @Param("state") String state);

    void bindSchool(@Param("openid")String openid, @Param("school")String school,@Param("state") String state);

    void bindUserContact(@Param("openid")String openid, @Param("qqid") String qqid, @Param("wxid") String wxid, @Param("phone") String phone, @Param("state") String state);

    String getSchoolByOpenid(String openid);

    int getId(String openid);

    User getUserById(int holder_id);

    List<String> getSchoolList();
}
