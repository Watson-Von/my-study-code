package com.watson.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
public class RedisUtil {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/7/2 9:39
     * @Description : 处理乱码问题
     */
    @Bean
    public RedisTemplate redisTemplateInit() {
        // key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //val实例化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 写入缓存
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
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
     * @version : 1.0
     * @description : 写入缓存设置时效时间
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
     */
    public boolean set(final String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 写入缓存设置时效, 默认秒为单位
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
     */
    public boolean setWithSeconds(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 批量删除对应的value
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 批量删除key
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 删除对应的value
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
     */
    public boolean remove(final String key) {
        if (exists(key)) {
            return redisTemplate.delete(key);
        }

        return false;
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 判断缓存中是否有对应的value
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:15
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 读取缓存
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 哈希 添加
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, value);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 哈希获取数据
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 列表添加
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 列表获取
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 集合添加
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 集合获取
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 有序集合添加
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 有序集合获取
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/8/27 16:14
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

}
