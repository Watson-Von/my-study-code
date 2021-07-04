package com.watson.demo.shareuserinfo;

/**
 * @author : fengHangWen
 * @version : 1.0
 * @Description : 用户上下文环境
 * @company : 深圳一点盐光科技有限公司
 * @date : 2021/7/4 10:21
 */
public class UserInfoContext {

    private static ThreadLocal<UserInfo> userInfo = new ThreadLocal<>();
    public static String KEY_USERINFO_IN_HTTP_HEADER = "X-AUTO-FP-USERINFO";

    public UserInfoContext() {
    }

    public static UserInfo getUser(){
        return (UserInfo)userInfo.get();
    }

    public static void setUser(UserInfo user){
        userInfo.set(user);
    }

}
