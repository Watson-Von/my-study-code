package com.watson.demo.excelhandle.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: s商家配置表
 * @Date: 2021/6/2 13:51
 **/
@Data
public class SupplySConfigDTO {

  @ExcelProperty(value = "'市场网格code'", index = 0)
  private String marketGridCode;

  @ExcelProperty(value = "'店铺id'", index = 2)
  private String storeId;

  @ExcelProperty(value = "店铺名称", index = 3)
  private String storeName;

}
