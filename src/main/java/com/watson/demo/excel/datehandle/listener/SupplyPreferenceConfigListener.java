package com.watson.demo.excel.datehandle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.watson.demo.excel.datehandle.dto.SupplyPreferenceConfigDTO;
import com.watson.demo.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: fengHangWen
 * @Description: 用于生成 喜好商家配置表 的初始化脚本
 * @Date: 2021/6/2 13:53
 **/
@Slf4j
public class SupplyPreferenceConfigListener extends AnalysisEventListener<SupplyPreferenceConfigDTO> {

    private static final String TABLE_NAME = "supply_preference_config";
    private static final List<String> COUNLUM_LIST =
            Lists.newArrayList(
                    "id", "garage_company_id",
                    "car_brand_id", "location_id",
                    "business_category_name", "store_id");

    private List<SupplyPreferenceConfigDTO> excelDataList = new ArrayList<>();

    @Override
    public void invoke(SupplyPreferenceConfigDTO supplyPreferenceConfigDTO, AnalysisContext analysisContext) {

        excelDataList.add(supplyPreferenceConfigDTO);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        log.info("================================所有数据解析完成=================================");

        log.info("一共解析到喜好商家配置: {} 条数据", excelDataList.size());

        List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

        for (int i = 0; i < excelDataList.size(); i++) {

            SupplyPreferenceConfigDTO dto = excelDataList.get(i);

            insertDataList.add(Lists.newArrayList(
                    new SqlUtils.ValueType(SqlUtils.INT_TYPE, i + 1),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getGarageCompanyId()),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCarBrandId()),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getLocationId()),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getBusinessCategoryName()),
                    new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getStoreId())));

        }

        String result = SqlUtils.createInsertSqlScrip(TABLE_NAME, COUNLUM_LIST, insertDataList);

        log.info("生成的初始化 {} 表的数据:\r\n{}", TABLE_NAME, result);

    }

}
