package com.example.bootdemo.utils;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *  Redis常见的五大数据类型：
 *  redisTemplate.opsForValue();[String(字符串)]
 *  redisTemplate.opsForList();[List(列表)]
 *  redisTemplate.opsForSet();[Set(集合)]
 *  redisTemplate.opsForHash();[Hash(散列)]
 *  redisTemplate.opsForZSet();[ZSet(有序集合)]
 *  redisTemplate. [公共方法]
 * @author qq491
 */
@Component
public class RedisUtil {

    @Autowired
    public RedisTemplate redisTemplate;

    //- - - - - - - - - - - - - - - - - - - - -  commons - - - - - - - - - - - - - - - - - - - -
    /**
     * 给一个指定的 key 值附加过期时间
     */
    public boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 移除指定key 的过期时间
     */
    public boolean persist(String key) {
        return redisTemplate.boundValueOps(key).persist();
    }

    /**
     * 移除指定key
     */
    public boolean delete(String ...key) {
        return redisTemplate.delete(key);
    }
}
