package com.watson.demo.wechat.pay.common;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : fengHangWen
 * @CreateDate : 2020/6/28 14:02
 * @Description : xml 工具类
 */
public class XmlUtil {

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/28 14:02
     * @Description : map 转 xml
     */
    public static String mapToXml(Map<String, String> parm, boolean isAddCDATA) {

        StringBuilder strbuff = new StringBuilder("<xml>");
        if (parm != null && !parm.isEmpty()) {
            for (Map.Entry<String, String> entry : parm.entrySet()) {
                strbuff.append("<").append(entry.getKey()).append(">");
                if (isAddCDATA) {
                    strbuff.append("<![CDATA[");
                    if (StringUtils.isNotEmpty(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                    strbuff.append("]]>");
                } else {
                    if (StringUtils.isNotEmpty(entry.getValue())) {
                        strbuff.append(entry.getValue());
                    }
                }
                strbuff.append("</").append(entry.getKey()).append(">");
            }
        }
        return strbuff.append("</xml>").toString();
    }

    /**
     * @author : fengHangWen
     * @CreateDate : 2020/6/28 14:05
     * @Description : xml 转 map
     */
    public static Map<String, String> xmlToMap(String xml) throws XmlPullParserException, IOException {

        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Map<String, String> map = new HashMap<>();
        XmlPullParser pullParser = XmlPullParserFactory.newInstance().newPullParser();
        // 为xml设置要解析的xml数据
        pullParser.setInput(inputStream, "UTF-8");
        int eventType = pullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    map = new HashMap<>();
                    break;
                case XmlPullParser.START_TAG:
                    String key = pullParser.getName();
                    if (key.equals("xml")) {
                        break;
                    }
                    String value = pullParser.nextText();
                    map.put(key, value);
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = pullParser.next();
        }
        return map;
    }

    public static void main(String[] args) {
        String xml = "<xml>\n" +
                "  <appid><![CDATA[wx2421b1c4370ec43b]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[10000100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1409811653]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>\n" +
                "  <time_end><![CDATA[20140903131540]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                "  <coupon_count><![CDATA[1]]></coupon_count>\n" +
                "  <coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                "  <coupon_id><![CDATA[10000]]></coupon_id>\n" +
                "  <coupon_fee><![CDATA[100]]></coupon_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>\n" +
                "</xml>";
        try {
            Map<String, String> stringStringMap = xmlToMap(xml);

            System.out.println(stringStringMap);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

    }


}
