package com.xiaoyuanbang.order.utils;

import com.xiaoyuanbang.order.dao.OrderDao;
import com.xiaoyuanbang.order.domain.REQUEST_CONSTANT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class OverDueUtil {
    @Autowired
    OrderDao orderDao;

    private static final Logger logger = LoggerFactory.getLogger(OverDueUtil.class);
    @Scheduled(cron = "0 5 0 * * ?")
//    @Scheduled(fixedRate = 1000)
    public void setOverDue(){
        try{
            logger.info("===更新过期Request数据===");
            int count = orderDao.setOverDue(REQUEST_CONSTANT.STATE_OVERDUE,REQUEST_CONSTANT.STATE_CREATE);
            logger.info("更新成功，过期数据"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("ERROE"+e.getMessage());
        }
    }
}
