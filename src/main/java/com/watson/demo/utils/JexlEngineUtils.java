package com.watson.demo.utils;

import org.apache.commons.jexl3.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class JexlEngineUtils {

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
        individualIncomeTaxMap.put("a", new BigDecimal(1));
        individualIncomeTaxMap.put("b", new BigDecimal(1));
        individualIncomeTaxMap.put("c", new BigDecimal(1));
        System.out.println(evaluateExpression(individualIncomeTaxMap, "a+b+c/d", 0));
    }

}
