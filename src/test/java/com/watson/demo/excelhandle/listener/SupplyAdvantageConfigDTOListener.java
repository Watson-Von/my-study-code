package com.watson.demo.excelhandle.listener;

import com.alibaba.excel.context.AnalysisContext;

import com.google.common.collect.Lists;
import java.util.List;

import com.watson.demo.excelhandle.dto.SupplyAdvantageConfigDTO;
import com.watson.demo.excelhandle.enums.CarBrandEnum;
import com.watson.demo.excelhandle.listener.base.BaseListener;
import com.watson.demo.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: fengHangWen
 * @Description: 用于生成 优势商家配置表 的初始化脚本
 * @Date: 2021/6/2 13:53
 **/
@Slf4j
public class SupplyAdvantageConfigDTOListener extends BaseListener<SupplyAdvantageConfigDTO> {

  public SupplyAdvantageConfigDTOListener(
      String excelFilePath,
      String sheetName,
      String tableName,
      List<String> counlumList) {
    super(excelFilePath, sheetName, tableName, counlumList);
  }

  @Override
  public void doAfterAllAnalysed(List<SupplyAdvantageConfigDTO> list) {

  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

    String supplyType;
    String description;
    for (SupplyAdvantageConfigDTO dto : getExcelDataList()) {

      supplyType = dto.getSupplyType();
      if ("本地".equals(supplyType)) {
        supplyType = "LOCAL";
      }
      if ("异地".equals(supplyType)) {
        supplyType = "NATION";
      }

      String marketGridCode = dto.getMarketGridCode();
      if ("龙华坂田".equals(marketGridCode)) {
        marketGridCode = "SZ001";
      }
      if ("深圳北".equals(marketGridCode)) {
        marketGridCode = "SZ002";
      }

      String carBrandName = dto.getCarBrandName();
      // 根据车辆品牌中文名称翻译成车辆Code
      String carBrandCode = CarBrandEnum.getCarBrandCodeByName(carBrandName);
      dto.setCarBrandId(carBrandCode);

      description = dto.getCarBrandName()
          // + "-" + dto.getLocationName()
          + "-" + dto.getSupplyType()
          + "-" + dto.getStoreName();

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, marketGridCode),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCarBrandId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getLocationId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getBusinessCategoryName()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplyType),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getStoreId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, description),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system"),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system")));

    }

    super.printInsertSqlScript(insertDataList);

  }

}
