package com.xiaoyuanbang.lostandfound.dao;

import com.xiaoyuanbang.lostandfound.domain.LostInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostInfoDao {

    void addRecord(LostInfo lostInfo);

    List<LostInfo> getLostInfo(String school, String state);

    LostInfo getRecordDetail(int id);

    void setRecordFinish(int id, String state);

    List<LostInfo> Search(@Param("s1") String s1, @Param("s2") String s2,@Param("s3") String s3,@Param("s4") String s4,@Param("s5") String s5 ,@Param("school") String school,@Param("state")String state);

    List<LostInfo> getLostInfoAsOwner(int id);

    int getHolderById(int id);
}
