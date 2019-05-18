package com.xiaoyuanbang.lostandfound.dao;

import com.xiaoyuanbang.lostandfound.domain.LostInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LostInfoDao {

    void addRecord(LostInfo lostInfo);

    List<LostInfo> getLostInfo(String school, int type, String state);

    LostInfo getRecordDetail(int id);

    void setRecordFinish(int id, String state);
}
