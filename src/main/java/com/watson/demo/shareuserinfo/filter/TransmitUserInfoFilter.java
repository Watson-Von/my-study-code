package com.watson.demo.shareuserinfo.filter;

import com.alibaba.fastjson.JSON;
import com.watson.demo.shareuserinfo.UserInfo;
import com.watson.demo.shareuserinfo.UserInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Slf4j
public class TransmitUserInfoFilter implements Filter {

    public TransmitUserInfoFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        this.initUserInfo((HttpServletRequest) request);
        chain.doFilter(request, response);

    }

    private void initUserInfo(HttpServletRequest request) {
        String userJson = request.getHeader("KEY_USERINFO_IN_HTTP_HEADER");
        if (StringUtils.isNotBlank(userJson)) {
            try {
                userJson = URLDecoder.decode(userJson, "UTF-8");
                UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
                // 将UserInfo放入上下文中
                UserInfoContext.setUser(userInfo);
            } catch (UnsupportedEncodingException e) {
                log.error("init userInfo error", e);
            }
        }
    }

    @Override
    public void destroy() {
    }

}
