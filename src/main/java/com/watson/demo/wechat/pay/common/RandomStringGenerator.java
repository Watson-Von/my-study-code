package com.watson.demo.wechat.pay.common;

import java.util.Random;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/28 15:03
 * @Description : 随机字符串生成
 */
public class RandomStringGenerator {
    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
