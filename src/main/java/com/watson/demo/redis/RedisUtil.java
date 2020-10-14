package com.watson.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/15 14:09
     * @Description : 设置值
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/15 14:10
     * @Description : 设置值(过期时间)
     */
    public boolean set(final String key, Object value, long timeout, TimeUnit unit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value, timeout, unit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/15 14:34
     * @Description : 获取缓存值
     */
    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/15 14:29
     * @Description : 删除
     */
    public boolean delete(final String key) {
        return redisTemplate.delete(key);
    }

}
