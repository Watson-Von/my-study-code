package com.watson.demo.api.version;

import com.watson.demo.api.version.annotation.APIVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fengHangWen
 * @Description:
 * @Date: 2021/6/19 14:23
 **/
@RestController
public class TestController {

    /**
     * @Author: fengHangWen
     * @Description: 版本 v4 接口
     * @Date: 2021/6/19 14:20
     **/
    @GetMapping(value = "/api/user")
    @APIVersion({"v4"})
    public int right4() {
        return 4;
    }

    /**
     * @Author: fengHangWen
     * @Description: 版本 v3 接口
     * @Date: 2021/6/19 14:20
     **/
    @GetMapping(value = "/api/user")
    @APIVersion("v3")
    public int right3() {
        return 3;
    }

    /**
     * @Author: fengHangWen
     * @Description: 版本 v1, v2 都路由到这里
     * @Date: 2021/6/19 14:19
     **/
    @GetMapping(value = "/api/user")
    @APIVersion({"v1", "v2"})
    public int right1To2() {
        return 1;
    }

}
