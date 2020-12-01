package com.watson.demo.utils;

import org.apache.commons.jexl3.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class JexlEngineUtils {

    /**
     * @author : fengHangWen
     * @version : 1.0
     * @description : map 里面如果 value 是 String 会计算错误
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 18:16
     */
    public static BigDecimal evaluateExpression(Map<String, Object> map,
                                                String expression,
                                                int scale) {

        JexlEngine jexl = new JexlBuilder().create();
        JexlContext jexlContext = new MapContext();
        //需要set源代码Math, 否则报错
        // jexlContext.set("Math", Math.class);
        //需要set源代码 BigDecimal, 否则报错
        //jexlContext.set("BigDecimal", BigDecimal.class);

        map.forEach(jexlContext::set);

        JexlExpression jexlExpression = jexl.createExpression(expression);
        try {
            Object obj = jexlExpression.evaluate(jexlContext);
            BigDecimal result = new BigDecimal(null == obj ? "0" : obj.toString());
            System.out.println(jexlExpression.getParsedText() + " = " + result);
            return result.setScale(scale, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            return null;
        }
    }

    //因为精度问题引入BigDecimal
    public static void main(String[] args) {
        Map<String, Object> individualIncomeTaxMap = new HashMap<>(16);
        individualIncomeTaxMap.put("a", new BigDecimal(5000));
        individualIncomeTaxMap.put("b", new BigDecimal(5000));
        individualIncomeTaxMap.put("c", new BigDecimal(5000));
        individualIncomeTaxMap.put("d", new BigDecimal(5000));
        String a = "(a*0.1+b+c)/(d*50)";
        System.out.println(evaluateExpression(individualIncomeTaxMap, a, 2));
    }

}
