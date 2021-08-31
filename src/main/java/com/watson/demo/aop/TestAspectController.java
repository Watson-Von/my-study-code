package com.watson.demo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fengHangWen
 * @Description:
 * @Date: 2021/6/19 14:23
 **/
@RestController
@RequestMapping("/test")
public class TestAspectController {

    @Autowired
    private TestService testService;

    @GetMapping(value = "/aspect")
    public void aspect() {
        testService.test("SZ001");
    }

}
