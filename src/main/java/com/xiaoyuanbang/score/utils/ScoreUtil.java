package com.xiaoyuanbang.score.utils;

import com.xiaoyuanbang.order.domain.RequestInfo;
import com.xiaoyuanbang.user.domain.UserScoreInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class ScoreUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScoreUtil.class);
    public void computeUserScore(UserScoreInfo userScoreInfo) {
        double acceptScore;
        double requestScore;
        double lostScore;
        try{
            if(userScoreInfo.getAcceptNum()>200){
                acceptScore = 1;
            }else {
                acceptScore = Math.sin(((double) userScoreInfo.getAcceptNum()/(4*200/Math.PI)));
            }
            if(userScoreInfo.getRequestNum()>100){
                requestScore = 1;
            }else {
                requestScore = Math.sin(((double) userScoreInfo.getRequestNum()/(4*100/Math.PI)));
            }
            if(userScoreInfo.getLostNum()>60){
                lostScore = 1;
            }else {
                lostScore = Math.sin(((double) userScoreInfo.getLostNum()/(4*60/Math.PI)));
            }
            double userScore = 0.4*requestScore+0.2*lostScore+0.4*acceptScore;
            userScoreInfo.setScore(userScore);

        }catch (Exception e){
            e.printStackTrace();
            logger.info("UserScore更新失败:"+userScoreInfo.getId());
        }
    }



    public void computeRequestScore(RequestInfo requestInfo, double userScore) {
        double priceScore;
        double finTimeScore;
        double requestScore;
        int days;
        Calendar today = Calendar.getInstance();
        Calendar finTime = Calendar.getInstance();
        finTime.setTime(requestInfo.getFintime());
        try {
            if(requestInfo.getPrice()>20){
                priceScore = 1;
            }else {
                priceScore = Math.sin(((double) requestInfo.getPrice()/(4*20/Math.PI)));
            }
            if(( days = finTime.get(Calendar.DATE)-today.get(Calendar.DATE))>15){
                finTimeScore = 0;
            }else {
                finTimeScore = Math.cos(((double) days/(4*15/Math.PI)));
            }
            requestScore =0.6*(0.3*finTimeScore+0.7*priceScore)+0.4*userScore;
            requestInfo.setScore(requestScore);

        }catch (Exception e){
            e.printStackTrace();
            logger.info("RequestScore更新失败:"+requestInfo.getReqid());
        }
    }
}
