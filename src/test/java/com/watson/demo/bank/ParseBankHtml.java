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

public class ParseBankHtml {

    public static void main(String[] args) {

        Map<String, String> provinceMap = getProvince();
        String city_url = "https://www.kaifx.cn/tp/Fun/dede_wd_city.php";
        Map<String, Object> param = new HashMap<>();
        Map<String, Map<String, String>> province2CityMap = new HashMap<>();
        provinceMap.forEach((k, v) -> {
            param.put("province", k);
            try {
                province2CityMap.put(k, getStringStringMap(OkHttpUtil.post(city_url, param)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (province2CityMap.isEmpty()) {
            return;
        }

        List<BankExcel> bankExcelList = new ArrayList<>();

        getBankMap().forEach((bankId, bankName) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("bank_id", Integer.parseInt(bankId));
            province2CityMap.forEach((provinceId, cityMap) -> {
                map.put("province_id", Integer.parseInt(provinceId));
                cityMap.forEach((cityId, cityName) -> {
                    map.put("city_id", cityId);
                    String url = "https://www.kaifx.cn/tp/Fun/btnQuanguo.php";
                    try {
                        String post = OkHttpUtil.post(url, map);
                        System.out.println(provinceMap.get(provinceId) + "_" + cityName + "_" + bankName + "_swift code是:" + post);

                        String code = "";
                        String[] split = post.split("--");
                        if (split.length > 1) {
                            code = split[split.length - 1];
                        }
                        bankExcelList.add(new BankExcel(bankName, provinceMap.get(provinceId), cityName, post, code));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        });

        if (CollectionUtils.isEmpty(bankExcelList)) {
            return;
        }

        GeneratorExcelFileService generatorExcelFileService = new GeneratorExcelFileService();
        generatorExcelFileService.generatorExcel("全国银行swift code代码version1.0", BankExcel.class, bankExcelList);
    }

    private static Map<String, String> getBankMap() {
        return new HashMap<String, String>() {{
            put("1", "中国银行");
            put("2", "中国工商银行");
            put("3", "中国农业银行");
            put("4", "中国建设银行");
            put("5", "中国交通银行");
            put("6", "兴业银行");
            put("7", "招商银行");
            put("8", "深圳发展银行");
            put("9", "浦发银行");
            put("10", "中信银行");
            put("11", "中国民生银行");
            put("12", "华夏银行");
        }};
    }

    private static Map<String, String> getProvince() {

        String provinceHtml = "<option value='0'>请选择省份</option>\n" +
                "<option value='55'>安徽</option>\n" +
                "<option value='75'>北京</option>\n" +
                "<option value='94'>重庆</option>\n" +
                "<option value='139'>福建</option>\n" +
                "<option value='149'>甘肃</option>\n" +
                "<option value='166'>广东</option>\n" +
                "<option value='188'>广西</option>\n" +
                "<option value='203'>贵州</option>\n" +
                "<option value='214'>海南</option>\n" +
                "<option value='237'>河北</option>\n" +
                "<option value='249'>河南</option>\n" +
                "<option value='268'>黑龙江</option>\n" +
                "<option value='282'>湖北</option>\n" +
                "<option value='300'>湖南</option>\n" +
                "<option value='315'>吉林</option>\n" +
                "<option value='325'>江苏</option>\n" +
                "<option value='339'>江西</option>\n" +
                "<option value='351'>辽宁</option>\n" +
                "<option value='366'>内蒙古</option>\n" +
                "<option value='380'>宁夏</option>\n" +
                "<option value='386'>青海</option>\n" +
                "<option value='395'>山东</option>\n" +
                "<option value='413'>山西</option>\n" +
                "<option value='425'>陕西</option>\n" +
                "<option value='436'>上海</option>\n" +
                "<option value='456'>四川</option>\n" +
                "<option value='477'>天津</option>\n" +
                "<option value='495'>西藏</option>\n" +
                "<option value='503'>新疆</option>\n" +
                "<option value='521'>云南</option>\n" +
                "<option value='538'>浙江</option>";

        return getStringStringMap(provinceHtml);
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
