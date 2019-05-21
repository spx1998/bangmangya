package com.xiaoyuanbang.common.mybatiscache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisAskDataException;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache implements Cache {

    private final String id;
//  private RedisTemplate redisTemplate;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        Jedis jedis=null;
        try{
            jedis = JedisPoolManager.getMgr().getCacheResource();
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
            /**
             * 过期处理
             */
            jedis.hset(this.id.getBytes(),serializer.serialize(key),serializer.serialize(value));
            jedis.expire(this.id.getBytes()+":"+serializer.serialize(key),1200);
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    @Override
    public Object getObject(Object key) {
        Object  result =null;
        Jedis jedis =null;
        try{
            jedis = JedisPoolManager.getMgr().getCacheResource();
            RedisSerializer<Object> serializer =new JdkSerializationRedisSerializer();
            result = serializer.deserialize(jedis.hget(this.id.getBytes(),serializer.serialize(key)));
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        Object result = null;
        Jedis jedis = null;
        try {
            jedis = JedisPoolManager.getMgr().getCacheResource();
            RedisSerializer<Object> serializer =new JdkSerializationRedisSerializer();
            result = jedis.expire(this.id.getBytes()+":"+serializer.serialize(key),0);
        }catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close(); // 释放资源还给连接池
            }
        }
        return result;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        int size =0;
        Jedis jedis =null;
        try{
            jedis = JedisPoolManager.getMgr().getCacheResource();
            size =Integer.valueOf(jedis.dbSize().toString());
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }
        finally {
            if(jedis!= null){
                jedis.close();
            }
        }
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
