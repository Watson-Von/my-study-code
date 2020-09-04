package com.watson.demo.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : fengHangWen
 * @version : 1.0
 * @description : OKHttp 工具类
 * @company : 深圳一点盐光科技有限公司
 * @date : 2020/9/4 12:04
 */
@Slf4j
@Component
public class OkHttpUtil {

    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build();


    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code. " + response);
        }
    }

    public String post(String url, Map<String, Object> map) throws IOException {
        RequestBody formBody = makeBuild(map).build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * 参数创建
     *
     * @param map
     */
    private FormBody.Builder makeBuild(Map<String, Object> map) {
        FormBody.Builder build = new FormBody.Builder();
        if (map != null) {
            for (String key : map.keySet()) {
                build.add(key, String.valueOf(map.get(key)));
            }
        }
        return build;
    }

    public static void main(String[] args) {

        // post请求, map 对象封装 post 请求入参, 入参是一个对象
        Map<String, Object> map = new HashMap<>();
        map.put("age", 12);
        map.put("name", "watson");

        String url = "url";
        try {
            post(url, com.alibaba.fastjson.JSON.toJSONString(map));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
