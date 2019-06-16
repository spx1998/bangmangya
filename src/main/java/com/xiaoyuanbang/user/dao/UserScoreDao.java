package com.xiaoyuanbang.user.dao;

import com.xiaoyuanbang.user.domain.UserScoreInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserScoreDao {
     List<UserScoreInfo> getUserScoreInfos() ;

    void createUser(String openid);

    void updateScore(List<UserScoreInfo> userScoreInfos);

    void addAcceptNum(String openid);

    void addRequestNum(String openid);

    void addLostNum(String openid);
}
