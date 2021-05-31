package com.watson.demo.redis.single.point.login.config;

import com.alibaba.fastjson.JSONObject;
import com.watson.demo.redis.RedisUtil;
import com.watson.demo.redis.single.point.login.common.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/23 16:15
 * @Description : 登陆拦截器
 */
@Configuration
public class RedisSessionInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        // 无论访问的地址是不是正确的, 都进行登录验证, 登录成功后的访问再进行分发, 404的访问自然会进入到错误控制器中
        HttpSession session = request.getSession();
        if (session.getAttribute("loginSessionId") != null) {
            try {
                // 验证当前请求的session是否是已登录的session
                String sessionId = (String) redisUtil.get("loginUser:" + session.getAttribute("loginSessionId"));
                System.out.println("用户已登录，sessionId为： " + sessionId);
                if (sessionId != null && sessionId.equals(session.getId())) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            System.out.println("用户未登录！");
            response.getWriter().print(JSONObject.toJSONString(new ResponseResult<>(404, "用户未登录！", null)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex)
            throws Exception {

    }


}
