package com.watson.demo.redis.single.point.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/spring")
public class HelloController {

    @RequestMapping(value = "/setSessionId", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> SetSessionId(HttpServletRequest request) {
        request.getSession().setAttribute("SessionKey", "XiaoHong");
        Map<String, Object> map = new HashMap<>();
        map.put("SessionKey", "XiaoHong");
        return map;
    }

    @RequestMapping(value = "/getSessionId", method = RequestMethod.GET)
    @ResponseBody
    public Object GetSessionId(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("SessionKey", request.getSession().getAttribute("SessionKey"));
        return map;
    }

}
