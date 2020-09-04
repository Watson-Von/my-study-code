package com.watson.demo.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static Date stringToDate(String str, String pattern) throws ParseException {
        // 判断pattern是否为空,利用工具类
        if (StringUtils.isEmpty(pattern)) {
            // 如果为空，设置默认格式
            pattern = DEFAULT_PATTERN;
        }
        if (StringUtils.isEmpty(str)) {
            // 如果为空，设置默认格式
            return null;
        }

        SimpleDateFormat sd = new SimpleDateFormat();
        sd.applyPattern(pattern);
        return sd.parse(str);
    }

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : 计算两个日期的月差
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/9/4 11:59
     */
    public static Integer getDifMonth(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        int result = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
        int month = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    public static final String datetimetoStr(Date date) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date != null) {
            return simpledateformat.format(date);
        }
        return "";
    }

    public static void main(String[] args) {

        try {
            Date date = DateUtil.stringToDate("2019-05-19T02:40:29.923Z", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            System.out.println(datetimetoStr(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
