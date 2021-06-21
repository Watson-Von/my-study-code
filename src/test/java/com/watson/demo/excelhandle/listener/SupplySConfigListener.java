package com.watson.demo.excelhandle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.dto.SupplySConfigDTO;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.listener.base.BaseListener;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.util.SqlUtils;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: fengHangWen
 * @Description: 用于生成 s商家配置表 的初始化脚本
 * @Date: 2021/6/2 13:53
 **/
@Slf4j
public class SupplySConfigListener extends BaseListener<SupplySConfigDTO> {

  public SupplySConfigListener(
      String excelFilePath,
      String sheetName,
      String tableName,
      List<String> counlumList) {
    super(excelFilePath, sheetName, tableName, counlumList);
  }


  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

    for (SupplySConfigDTO supplySConfigDTO : getExcelDataList()) {

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplySConfigDTO.getMarketGridCode()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplySConfigDTO.getStoreId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, supplySConfigDTO.getStoreName()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system"),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system")));

    }

    super.printInsertSqlScript(insertDataList);

  }

}
