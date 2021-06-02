package com.watson.demo.excel.datehandle.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: 喜好商家配置表
 * @Date: 2021/6/2 16:19
 **/
@Data
public class SupplyPreferenceConfigDTO {

    @ExcelProperty(value = "'维修厂id'", index = 0)
    private String garageCompanyId;

    @ExcelProperty(value = "'汽车品牌id'", index = 1)
    private String carBrandId;

    @ExcelProperty(value = "'本地id'", index = 2)
    private String locationId;

    @ExcelProperty(value = "'业务一级分类名称'", index = 3)
    private String businessCategoryName;

    @ExcelProperty(value = "'店铺id'", index = 4)
    private String storeId;


}
