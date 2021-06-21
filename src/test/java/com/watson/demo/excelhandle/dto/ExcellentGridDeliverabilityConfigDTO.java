package com.watson.demo.excelhandle.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: 网格供应能力配置
 * @Date: 2021/6/10 16:03
 **/
@Data
public class ExcellentGridDeliverabilityConfigDTO {

  @ExcelProperty(value = "", index = 1)
  private String marketGridCode;

  @ExcelProperty(value = "", index = 2)
  private String carLevel;

  @ExcelProperty(value = "", index = 3)
  private String carBrandId;

  @ExcelProperty(value = "", index = 4)
  private String locationId;

  @ExcelProperty(value = "", index = 5)
  private String businessScenario;

  @ExcelProperty(value = "", index = 6)
  private String businessCategoryName;

  @ExcelProperty(value = "", index = 7)
  private String supplySourceGeoId;

  @ExcelProperty(value = "", index = 8)
  private String supplySourceGeoType;

  @ExcelProperty(value = "", index = 9)
  private String supplySourceName;

  @ExcelProperty(value = "", index = 10)
  private String supplySourceLevel;

  @ExcelProperty(value = "", index = 11)
  private String supplySourceArea;

}
