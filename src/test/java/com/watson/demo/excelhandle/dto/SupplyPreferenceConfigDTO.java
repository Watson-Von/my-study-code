package com.watson.demo.excelhandle.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: 喜好商家配置表
 * @Date: 2021/6/2 16:19
 **/
@Data
public class SupplyPreferenceConfigDTO {

  @ExcelProperty(value = "维修厂id", index = 0)
  private String garageCompanyId;

  @ExcelProperty(value = "维修厂Name", index = 1)
  private String garageCompanyName;

  @ExcelProperty(value = "汽车品牌code", index = 2)
  private String carBrandId;

  @ExcelProperty(value = "汽车品牌名称", index = 3)
  private String carBrandName;

  // @ExcelProperty(value = "主机上id", index = 4)
  private String locationId;

  // @ExcelProperty(value = "主机上名称", index = 5)
  private String locationName;

  @ExcelProperty(value = "业务一级分类名称", index = 4)
  private String businessCategoryName;

  @ExcelProperty(value = "店铺id", index = 5)
  private String storeId;

  @ExcelProperty(value = "店铺名称", index = 6)
  private String storeName;


}
