package com.xiaoyuanbang.score.service;

import com.xiaoyuanbang.order.dao.OrderDao;
import com.xiaoyuanbang.order.domain.REQUEST_CONSTANT;
import com.xiaoyuanbang.order.domain.RequestInfo;
import com.xiaoyuanbang.score.utils.ScoreUtil;
import com.xiaoyuanbang.user.dao.UserDao;
import com.xiaoyuanbang.user.dao.UserScoreDao;
import com.xiaoyuanbang.user.domain.User;
import com.xiaoyuanbang.user.domain.UserScoreInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@EnableScheduling
public class RequestScoreService {
    private static final Logger logger = LoggerFactory.getLogger(RequestScoreService.class);
    @Autowired
    OrderDao orderDao;
    @Autowired
    UserScoreDao userScoreDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ScoreUtil scoreUtil;
    @Scheduled(cron = "0 15 0 * * ?")
    public void updateRequestScore(){
        logger.info("更新Request积分");
        try{
            List<RequestInfo> requestInfos = orderDao.getAllRequest();
            List<User> userList = userDao.getAlluser();
            List<UserScoreInfo> userScoreInfos = userScoreDao.getUserScoreInfos();

            HashMap<String,Integer> userMap =new HashMap<>();
            HashMap<Integer,Double> userScoreMap =new HashMap<>();
            //降低时间复杂度->userScoreMap:key = holder_id , value = userScore
            for(User user:userList){
                userMap.put(user.getOpenid(),user.getId());
            }
            for (UserScoreInfo userScoreInfo:userScoreInfos){
                userScoreMap.put(userMap.get(userScoreInfo.getOpenid()), userScoreInfo.getScore());
            }
            for(RequestInfo requestInfo:requestInfos){
//                如果不为create状态 则不再更新score
                if(!requestInfo.getState().equals(REQUEST_CONSTANT.STATE_CREATE))
                    continue;
                double userScore = userScoreMap.get(requestInfo.getHolder_id());
                scoreUtil.computeRequestScore(requestInfo,userScore);
            }
            orderDao.setRequestScore(requestInfos);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("更新Request积分失败");
        }
        logger.info("Request积分更新成功");
    }
}
