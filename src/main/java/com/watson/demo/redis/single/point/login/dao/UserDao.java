package com.watson.demo.redis.single.point.login.dao;

import com.watson.demo.redis.single.point.login.vo.User;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private Map<Integer, User> userDataMap = new HashMap<>();

    public UserDao() {

        User user = new User(1, "Watson", "123456", 25, "深圳");

        userDataMap.put(1, user);

    }

    public User getUserByUserNameAndUserPwd(String userName, String userPwd) {

        return userDataMap.get(1);

    }

    public User getById(Integer userId) {

        return userDataMap.get(userId);

    }

}
