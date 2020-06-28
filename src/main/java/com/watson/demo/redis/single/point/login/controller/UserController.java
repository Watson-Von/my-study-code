package com.watson.demo.redis.single.point.login.controller;

import com.watson.demo.redis.RedisUtil;
import com.watson.demo.redis.single.point.login.common.ResponseResult;
import com.watson.demo.redis.single.point.login.service.UserService;
import com.watson.demo.redis.single.point.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/login")
    public ResponseResult<User> login(HttpServletRequest request, String userName, String userPwd) {
        User user = userService.getUserByUserNameAndUserPwd(userName, userPwd);
        ResponseResult<User> resData = new ResponseResult<>();
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginSessionId", user.getId());
            redisUtil.set("loginUser:" + user.getId(), session.getId());
            resData.setData(user);
            resData.setStatus(0);
            resData.setMessage("登录成功！");
        } else {
            resData.setData(null);
            resData.setStatus(1);
            resData.setMessage("用户信息输入错误！");
        }
        return resData;
    }

    @RequestMapping(value = "/getUserInfo")
    public ResponseResult<User> get(Integer id) {
        User user = userService.getById(id);
        ResponseResult<User> resData = new ResponseResult<>();
        if (user != null) {
            resData.setData(user);
            resData.setStatus(0);
            resData.setMessage("查询成功！");
        } else {
            resData.setData(null);
            resData.setStatus(1);
            resData.setMessage("没有符合该查询条件的数据！");
        }
        return resData;
    }

}
