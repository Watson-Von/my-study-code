package com.watson.demo.excelhandle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.dto.ExcellentGridDeliverabilityConfigDTO;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.enums.CarBrandEnum;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.enums.LocationIdEnum;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.listener.base.BaseListener;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.util.SqlUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

/**
 * @Author: fengHangWen
 * @Description: 用于生成网格供应能力配置初始化脚本
 * @Date: 2021/6/10 16:14
 **/
@Slf4j
public class ExcellentGridDeliverabilityConfigDTOListener extends
    BaseListener<ExcellentGridDeliverabilityConfigDTO> {

  public ExcellentGridDeliverabilityConfigDTOListener(
      String excelFilePath,
      String sheetName,
      String tableName,
      List<String> counlumList) {
    super(excelFilePath, sheetName, tableName, counlumList);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

    for (ExcellentGridDeliverabilityConfigDTO dto : getExcelDataList()) {

      String carBrandId = dto.getCarBrandId();
      String carBrandCode = CarBrandEnum.getCarBrandCodeByName(carBrandId);
      if (StringUtils.isNotEmpty(carBrandCode)) {
        dto.setCarBrandId(carBrandCode);
      }

      String locationName = dto.getLocationId();
      String locationId = LocationIdEnum.getLocationIdByName(locationName);
      if (StringUtils.isNotEmpty(locationId)) {
        dto.setLocationId(locationId);
      }

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getMarketGridCode()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCarBrandId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getLocationId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCarLevel()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getBusinessScenario()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getBusinessCategoryName()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getSupplySourceGeoId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getSupplySourceGeoType()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getSupplySourceName()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getSupplySourceLevel()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getSupplySourceArea()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system"),
          new SqlUtils.ValueType(SqlUtils.TIME_TYPE, "now()"),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, "system"),
          new SqlUtils.ValueType(SqlUtils.TIME_TYPE, "now()")
      ));

    }

    super.printInsertSqlScript(insertDataList);

  }


}
