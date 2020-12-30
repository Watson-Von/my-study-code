package com.watson.demo.bank;

import com.watson.demo.excel.GeneratorExcelFileService;
import com.watson.demo.utils.OkHttpUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class LocalBankHtml {

    public static void main(String[] args) {

        Map<String, Map<String, String>> localBankMap = new HashMap<>();

        Map<String, String> bankMap = getBankMap();

        bankMap.forEach((bankID, bankName) -> {
            String url = "https://www.kaifx.cn/tp/Fun/dede_wd_city1.php";
            try {
                String cityHtml = OkHttpUtil.post(url, new HashMap<String, Object>() {{
                    put("bank_id", Integer.parseInt(bankID));
                }});
                Map<String, String> cityMap = getStringStringMap(cityHtml);
                localBankMap.put(bankID, cityMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (localBankMap.isEmpty()) {
            return;
        }

        List<LocalBankExcel> localBankExcelList = new ArrayList<>();

        localBankMap.forEach((bankID, cityMap) -> {
            String url = "https://www.kaifx.cn/tp/Fun/btnQuanguo1.php";
            Map<String, Object> param = new HashMap<>();
            param.put("bank_id", Integer.parseInt(bankID));
            cityMap.forEach((cityId, cityName) -> {
                param.put("city_id", Integer.parseInt(cityId));
                try {
                    String post = OkHttpUtil.post(url, param);
                    System.out.println(bankMap.get(bankID) + "_" + cityName + "_" + post);
                    String[] split = post.split("--");
                    String code = "";
                    if (split.length > 1) {
                        code = split[split.length - 1];
                    }
                    localBankExcelList.add(new LocalBankExcel(bankMap.get(bankID), cityName, post, code));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        if (CollectionUtils.isEmpty(localBankExcelList)) {
            return;
        }

        GeneratorExcelFileService generatorExcelFileService = new GeneratorExcelFileService();
        generatorExcelFileService.generatorExcel("地方性银行swift code代码", LocalBankExcel.class, localBankExcelList);

    }

    private static Map<String, String> getBankMap() {
        return new HashMap<String, String>() {{
//            put("1", "中国银行");
//            put("2", "中国工商银行");
//            put("3", "中国农业银行");
//            put("4", "中国建设银行");
//            put("5", "中国交通银行");
//            put("6", "兴业银行");
//            put("7", "招商银行");
//            put("8", "深圳发展银行");
//            put("9", "浦发银行");
//            put("10", "中信银行");
//            put("11", "中国民生银行");
//            put("12", "华夏银行");
            put("13", "上海银行");
            put("14", "北京银行");
            put("15", "南京银行");
            put("16", "宁波银行");
            put("17", "中国光大银行");
            put("19", "成都银行");
            put("20", "青岛银行");
            put("21", "哈尔滨银行");
            put("22", "重庆银行");
            put("23", "东莞银行");
            put("24", "东莞农村商业银行");
            put("25", "浙江稠州商业银行");
            put("26", "温州银行");
            put("27", "鄞州银行");
            put("28", "长沙银行");
            put("29", "潍坊银行");
            put("30", "平安银行");
            put("31", "徽商银行");
            put("32", "浙商银行");
            put("33", "浙江民泰商业银行");
            put("34", "广发银行");
            put("35", "宁波国际银行");
            put("36", "盛京银行");
            put("37", "包商银行");
            put("38", "齐商银行");
            put("39", "星展银行");
            put("47", "渤海银行");
        }};
    }


    private static Map<String, String> getStringStringMap(String provinceHtml) {

        if (StringUtils.isEmpty(provinceHtml)) {
            return new HashMap<>();
        }

        Document root = Jsoup.parse(provinceHtml);
        ListIterator<Element> option = root.getElementsByTag("option").listIterator();
        Map<String, String> provinceMap = new HashMap<>();

        String key;
        String value;
        while (option.hasNext()) {
            Element element = option.next();
            key = element.getElementsByAttribute("value").attr("value");
            value = element.getElementsByAttribute("value").text();
            if ("0".equals(key)) {
                continue;
            }
            provinceMap.put(key, value);
        }

        return provinceMap;
    }

}
