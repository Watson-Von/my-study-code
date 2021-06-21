package com.watson.demo.excelhandle.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: 优势商家配置表
 * @Date: 2021/6/2 16:27
 **/
@Data
public class SupplyAdvantageConfigDTO {

  @ExcelProperty(value = "市场网格code", index = 1)
  private String marketGridCode;

  @ExcelProperty(value = "汽车品牌id", index = 2)
  private String carBrandId;

  @ExcelProperty(value = "汽车品牌名称", index = 3)
  private String carBrandName;

  // @ExcelProperty(value = "主机厂ID", index = 3)
  @ExcelIgnore
  private String locationId;

  // @ExcelProperty(value = "主机厂名称", index = 4)
  @ExcelIgnore
  private String locationName;

  @ExcelProperty(value = "业务一级分类名称", index = 5)
  private String businessCategoryName;

  @ExcelProperty(value = "店铺id", index = 6)
  private String storeId;

  @ExcelProperty(value = "店铺名称", index = 7)
  private String storeName;

  @ExcelProperty(value = "供应类型(enum:LOCAL,NATION)", index = 8)
  private String supplyType;


}
