package com.watson.demo.excelhandle.listener;

import com.alibaba.excel.context.AnalysisContext;

import com.google.common.collect.Lists;
import java.util.List;

import com.watson.demo.excelhandle.dto.SupplyPreferenceConfigDTO;
import com.watson.demo.excelhandle.listener.base.BaseListener;
import com.watson.demo.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: fengHangWen
 * @Description: 用于生成 喜好商家配置表 的初始化脚本
 * @Date: 2021/6/2 13:53
 **/
@Slf4j
public class SupplyPreferenceConfigListener extends BaseListener<SupplyPreferenceConfigDTO> {

  public SupplyPreferenceConfigListener(
      String excelFilePath,
      String sheetName,
      String tableName,
      List<String> counlumList) {
    super(excelFilePath, sheetName, tableName, counlumList);
  }

  @Override
  public void doAfterAllAnalysed(List<SupplyPreferenceConfigDTO> list) {

  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

    String description;
    for (SupplyPreferenceConfigDTO dto : getExcelDataList()) {

      description = dto.getGarageCompanyName()
          + "-"
          + dto.getCarBrandName()
          + "-" + dto.getStoreName();

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getGarageCompanyId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCarBrandId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getBusinessCategoryName()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getStoreId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, description),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system"),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system"))
      );

    }

    super.printInsertSqlScript(insertDataList);

  }

}
