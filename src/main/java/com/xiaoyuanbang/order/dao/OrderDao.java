package com.xiaoyuanbang.order.dao;

import com.xiaoyuanbang.order.domain.RequestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;


@Mapper
public interface OrderDao {

     List<RequestInfo> getRequestList(@Param("school")String school,@Param("state") String state);

    List<RequestInfo> getRequestListByType(@Param("school") String school, @Param("type") String type,@Param("state")  String state);

    RequestInfo getRequest(int reqid);

    int setRequestConfirm(@Param("reqid") int reqid, @Param("userid") int userid, @Param("stateAfter") String state,@Param("stateNow") String stateCreate);

    void createRequest(@Param("name") String name, @Param("description") String description, @Param("fintime") Date fintime, @Param("school") String school, @Param("type") String type, @Param("price") int price,@Param("holder_id") int userid);

    List<RequestInfo> getRequestAsHolder(int userid);

    List<RequestInfo> getRequestAsWorker(int userid);

    int getHolderId(int reqid);

    String getType(int reqid);

    int setRequestState(@Param("reqid") int reqid,@Param("stateAfter") String stateAfter,@Param("stateNow") String stateNow);
}

