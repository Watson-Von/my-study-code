package com.watson.demo.shareuserinfo.intercepter;

import com.alibaba.fastjson.JSON;
import com.watson.demo.shareuserinfo.UserInfo;
import com.watson.demo.shareuserinfo.UserInfoContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class TransmitUserInfoFeighClientIntercepter implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        // 从应用上下文中取出user信息，放入Feign的请求头中
        UserInfo user = UserInfoContext.getUser();
        if (user != null) {
            try {
                String userJson = JSON.toJSONString(user);
                requestTemplate.header("KEY_USERINFO_IN_HTTP_HEADER", URLDecoder.decode(userJson,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("用户信息设置错误",e);
            }
        }

    }
}

