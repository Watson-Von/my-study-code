package com.watson.demo.excel.datehandle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.watson.demo.excel.datehandle.dto.SupplySConfigDTO;
import com.watson.demo.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fengHangWen
 * @Description: 用于生成 s商家配置表 的初始化脚本
 * @Date: 2021/6/2 13:53
 **/
@Slf4j
public class SupplySConfigListener extends AnalysisEventListener<SupplySConfigDTO> {

    private static final String TABLE_NAME = "supply_s_config";
    private static final List<String> COUNLUM_LIST =
            Lists.newArrayList("id", "market_grid_code", "store_id", "store_name");

    private List<SupplySConfigDTO> excelDataList = new ArrayList<>();

    @Override
    public void invoke(SupplySConfigDTO supplySConfigDTO, AnalysisContext analysisContext) {

        excelDataList.add(supplySConfigDTO);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        log.info("================================所有数据解析完成=================================");

        log.info("一共解析到s商家配置: {} 条数据", excelDataList.size());

        List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

        for (int i = 0; i < excelDataList.size(); i++) {

            SupplySConfigDTO supplySConfigDTO = excelDataList.get(i);

            insertDataList.add(Lists.newArrayList(
                    new SqlUtils.ValueType(SqlUtils.INT_TYPE, i + 1),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplySConfigDTO.getMarketGridCode()),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplySConfigDTO.getStoreId()),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplySConfigDTO.getStoreName())));

        }

        String result = SqlUtils.creataInsertSqlScrip(TABLE_NAME, COUNLUM_LIST, insertDataList);

        log.info("生成的初始化 {} 表的数据:\r\n{}", TABLE_NAME, result);

    }

}
