package com.watson.demo.utils;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fengHangWen
 * @Description: sql 工具类
 * @Date: 2021/6/2 13:58
 **/
@Slf4j
public class SqlUtils {

    public static final String STRING_TYPE = "String";
    public static final String INT_TYPE = "int";

    /**
     * @Author: fengHangWen
     * @Description: 封装插入数据的值
     * type: 插入数据的类型
     * value：插入数据的值
     * @Date: 2021/6/2 15:33
     **/
    @Data
    @AllArgsConstructor
    public static class ValueType {
        /**
         * @Author: fengHangWen
         * @Description: 值的类型
         * @Date: 2021/6/2 16:01
         **/
        private String type;

        /**
         * @Author: fengHangWen
         * @Description: 具体的值
         * @Date: 2021/6/2 16:02
         **/
        private Object value;
    }

    /**
     * @Author: fengHangWen
     * @Description: 创建基于mysql 的批量插入语句
     * tableName: 表名
     * columnList: 列集合
     * insertDataList： 封装需要生成 insert 语句的值
     * @Date: 2021/6/2 15:31
     **/
    public static String createInsertSqlScrip(String tableName,
                                              List<String> columnList,
                                              List<List<ValueType>> insertDataList) {

        attrVerification(tableName, columnList, insertDataList);

        return "INSERT INTO " + tableName +
                "\r\n" +
                "(" +
                String.join(", ", columnList) +
                ")" +
                "\r\n" +
                "VALUES" +
                "\r\n" +
                createInsertValueStatement(insertDataList) +
                ";";

    }

    /**
     * @Author: fengHangWen
     * @Description: 入参校验
     * @Date: 2021/6/2 15:40
     **/
    private static void attrVerification(String tableName,
                                         List<String> columnList,
                                         List<List<ValueType>> insertDataList) {

        if (StringUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("tableName 不能为空");
        }

        if (CollectionUtils.isEmpty(columnList)) {
            throw new IllegalArgumentException("列集合不能为空");
        }

        if (CollectionUtils.isEmpty(insertDataList)) {
            throw new IllegalArgumentException("封装需要生成 insert 语句的值");
        }

    }


    /**
     * @Author: fengHangWen
     * @Description: 生成插入值的语句, 例如:
     * ('耐克运动鞋','广州',500,1000,'003.jpg'),
     * ('耐克运动鞋2','广州2',500,1000,'002.jpg')
     * @Date: 2021/6/2 14:33
     **/
    private static String createInsertValueStatement(List<List<ValueType>> insertDataList) {

        if (CollectionUtils.isEmpty(insertDataList)) {
            throw new IllegalArgumentException("生成插入值的语句时, 基础数据不能为空");
        }

        List<String> list = Lists.newArrayList();
        insertDataList.forEach(valueTypeList -> list.add(createSingleInsertStatement(valueTypeList)));

        return String.join(",\r\n", list);

    }

    /**
     * @Author: fengHangWen
     * @Description: 生成单条插入值语句, 例如:
     * (' 耐克运动鞋 ', ' 广州 ', 500, 1000, ' 003.jpg ')
     * @Date: 2021/6/2 14:48
     **/
    private static String createSingleInsertStatement(List<ValueType> valueTypeList) {

        StringBuilder sb = new StringBuilder("(");
        List<String> list = new ArrayList<>();
        valueTypeList.forEach(valueType -> {

            String type = valueType.getType();
            Object value = valueType.getValue();

            switch (type) {
                case STRING_TYPE:
                    list.add("\'" + value + "\'");
                    break;
                case INT_TYPE:
                    list.add(value.toString());
                    break;
                default:
                    log.info("未知的值类型: {}", type);
            }

        });

        sb.append(String.join(", ", list));
        sb.append(")");
        return sb.toString();
    }

    public static void main(String[] args) {


        List<List<ValueType>> insertDataList = Lists.newArrayList();

        insertDataList.add(Lists.newArrayList(
                new ValueType(SqlUtils.INT_TYPE, 1),
                new ValueType(SqlUtils.STRING_TYPE, "市场网格code1"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺id1"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺名称1")));

        insertDataList.add(Lists.newArrayList(
                new ValueType(SqlUtils.INT_TYPE, 2),
                new ValueType(SqlUtils.STRING_TYPE, "市场网格code2"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺id2"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺名称2")));

        insertDataList.add(Lists.newArrayList(
                new ValueType(SqlUtils.INT_TYPE, 3),
                new ValueType(SqlUtils.STRING_TYPE, "市场网格code3"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺id3"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺名称3")));

        insertDataList.add(Lists.newArrayList(
                new ValueType(SqlUtils.INT_TYPE, 4),
                new ValueType(SqlUtils.STRING_TYPE, "市场网格code4"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺id4"),
                new ValueType(SqlUtils.STRING_TYPE, "店铺名称4")));

        ArrayList<String> columnList = Lists.newArrayList("id", "market_grid_code", "store_id", "store_name");

        String createInsertSqlScrip = createInsertSqlScrip("supply_s_config", columnList, insertDataList);

        System.out.println(createInsertSqlScrip);

    }

}
