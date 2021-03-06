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

    void createRequest(@Param("name") String name, @Param("description") String description, @Param("fintime") Date fintime, @Param("school") String school, @Param("type") String type, @Param("price") int price, @Param("holder_id") int userid,@Param("form_id") String formId);

    List<RequestInfo> getRequestAsHolder(int userid);

    List<RequestInfo> getRequestAsWorker(int userid);

    int getHolderId(int reqid);

    String getType(int reqid);

    int setRequestState(@Param("reqid") int reqid,@Param("stateAfter") String stateAfter,@Param("stateNow") String stateNow);

    List<RequestInfo> Search(@Param("s1") String s1, @Param("s2") String s2, @Param("s3") String s3, @Param("s4") String s4, @Param("s5") String s5, @Param("school") String school,@Param("state") String state);

    String getFormId(int reqid);

    int setOverDue(String stateOverDue,String stateCreate);

    List<RequestInfo> getAllRequest();

    void setRequestScore(List<RequestInfo> requestInfos);
}

