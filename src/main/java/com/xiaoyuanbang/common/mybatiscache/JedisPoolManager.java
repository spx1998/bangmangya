package com.xiaoyuanbang.common.mybatiscache;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolManager {
    private boolean testOnBorrow=true;
    private int maxIdle=8;
    boolean blockWhenExhausted=true;
    private int maxWaitMillis=-1;
    private int maxTotal=200;
    private String host="127.0.0.1";
    private int port=6379;
    private volatile static JedisPoolManager manager;
    private final  JedisPool poolCache;

    private JedisPoolManager() {
        try{
            JedisPoolConfig config =new JedisPoolConfig();
            config.setMaxIdle(maxIdle);
            config.setMaxTotal(maxTotal);
            config.setMaxWaitMillis(maxWaitMillis);
            config.setTestOnBorrow(testOnBorrow);
            config.setMinIdle(0);
//            new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            poolCache=new JedisPool(config,host,port);
        }catch (Exception e){
            throw new IllegalArgumentException("init JedisPool error",e.getCause());
        }
    }
    public static JedisPoolManager getMgr(){
        if(manager == null){
            synchronized (JedisPoolManager.class){
                if(manager == null){
                    manager=new JedisPoolManager();
                }
            }
        }
        return manager;
    }
    public Jedis getCacheResource() {

        return poolCache.getResource();
    }


    public void destroy() {
        // when closing your application:
        poolCache.destroy();
    }

    public void close() {
        poolCache.destroy();
    }

}
