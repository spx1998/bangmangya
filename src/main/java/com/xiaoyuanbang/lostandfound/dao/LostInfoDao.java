package com.xiaoyuanbang.lostandfound.dao;

import com.xiaoyuanbang.lostandfound.domain.LostInfo;

import java.util.List;

public interface LostInfoDao {

    void addRecord(LostInfo lostInfo);

    List<LostInfo> getLostInfo(String school, int type);

    LostInfo getRecordDetail(int id);

    void setRecordFinish(int id, String stateFinish);
}
