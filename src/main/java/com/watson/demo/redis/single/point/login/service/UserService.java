package com.watson.demo.redis.single.point.login.service;

import com.watson.demo.redis.single.point.login.dao.UserDao;
import com.watson.demo.redis.single.point.login.vo.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/23 16:14
     * @Description : 根据用户名和用户密码获取用户
     */
    public User getUserByUserNameAndUserPwd(String userName, String userPwd) {
        UserDao userDao = new UserDao();
        return userDao.getUserByUserNameAndUserPwd(userName, userPwd);
    }

    public User getById(Integer id) {
        UserDao userDao = new UserDao();
        return userDao.getById(id);
    }
}
