package com.xiaoyuanbang.common.mybatiscache;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolManager {
    @Value("${spring.redis.testOnBorrow}")
    boolean testOnBorrow;
    @Value("${spring.redis.maxIdle}")
    int maxIdle;
    @Value("${spring.redis.blockWhenExhausted}")
    boolean blockWhenExhausted;
    @Value("${spring.redis.maxWaitMillis}")
    int maxWaitMillis;
    @Value("${spring.redis.maxTotal}")
    int maxTotal;
    @Value("${spring.redis.host}")
    String host;
    @Value("${spring.redis.port}")
    String port;
    private volatile static JedisPoolManager manager;
//    private  final JedisPool pool;
    private final  JedisPool poolCache;

    private JedisPoolManager() {
        try{
            JedisPoolConfig config =new JedisPoolConfig();
            config.setMaxIdle(maxIdle);
            config.setMaxTotal(maxTotal);
            config.setMaxWaitMillis(maxWaitMillis);
            config.setTestOnBorrow(testOnBorrow);
            poolCache=new JedisPool(config,host,Integer.parseInt(port));
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
