package com.watson.demo.excel.datehandle.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: 优势商家配置表
 * @Date: 2021/6/2 16:27
 **/
@Data
public class SupplyAdvantageConfigDTO {

    @ExcelProperty(value = "'市场网格code'", index = 0)
    private String marketGridCode;

    @ExcelProperty(value = "'汽车品牌id'", index = 1)
    private String carBrandId;

    @ExcelProperty(value = "'本地id'", index = 2)
    private String locationId;

    @ExcelProperty(value = "'业务一级分类名称'", index = 3)
    private String businessCategoryName;

    @ExcelProperty(value = "供应类型(enum:LOCAL,NATION)", index = 3)
    private String supplyType;

    @ExcelProperty(value = "'店铺id'", index = 4)
    private String storeId;

}
