package com.xiaoyuanbang.user.dao;

import com.xiaoyuanbang.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserDao {

    String hasThisUser(String openid);

    void createNewUser(String userInfo);

    User getUser(String openid);

    void updateUserByOpenid(@Param("openid") String openid, @Param("name") String name, @Param("gender") String gender, @Param("state") String state);

    void bindSchool(@Param("openid")String openid, @Param("school")String school,@Param("state") String state);

    void bindUserContact(@Param("openid")String openid,@Param("qqid") int qqid,@Param("wxid") String wxid,@Param("phone") int phone,@Param("state") String state);

    String getSchoolByOpenid(String openid);

    int getId(String openid);

    User getUserById(int holder_id);
}
