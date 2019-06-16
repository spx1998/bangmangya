package com.xiaoyuanbang.score.service;

import com.xiaoyuanbang.user.dao.UserDao;
import com.xiaoyuanbang.user.dao.UserScoreDao;
import com.xiaoyuanbang.user.domain.UserScoreInfo;
import com.xiaoyuanbang.score.utils.ScoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
@EnableScheduling
public class UserScoreService {
    private static final Logger logger = LoggerFactory.getLogger(UserScoreService.class);
    @Autowired
    UserDao userDao;
    @Autowired
    UserScoreDao userScoreDao;
    @Autowired
    ScoreUtil scoreUtil;

    @Scheduled(cron = "0 10 0 * * ?")
    public void updateUserScore(){
        logger.info("更新用户积分");
        Calendar today =Calendar.getInstance();
        Calendar registerDate = Calendar.getInstance();
        try {
            List<UserScoreInfo> userScoreInfos =userScoreDao.getUserScoreInfos();
            for(UserScoreInfo userScoreInfo:userScoreInfos){
                //计算score
                scoreUtil.computeUserScore(userScoreInfo);
                //判断是否为新用户。如果为新用户，权值设为1 。
                registerDate.setTime(userScoreInfo.getRegisterDate());
                if(userScoreInfo.getRegisterReward()==1){
                    if((today.get(Calendar.DATE) -registerDate.get(Calendar.DATE))>7)
                        userScoreInfo.setRegisterReward(0);
                    else
                        userScoreInfo.setScore(1);
                }
            }
            userScoreDao.updateScore(userScoreInfos);
        }catch (Exception e){
            e.getMessage();
            logger.info("用户积分更新失败");
        }
        logger.info("用户积分更新成功");
    }
}
