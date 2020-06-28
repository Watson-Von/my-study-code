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


}
