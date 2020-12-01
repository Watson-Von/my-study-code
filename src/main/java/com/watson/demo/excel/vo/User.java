package com.watson.demo.excel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    @ExcelProperty(value = "名称", index = 0)
    private String name;

    @ExcelProperty(value = "时间", index = 1)
    private Date time;

    @NumberFormat("#.##%")
    @ExcelProperty(value = "完成率", index = 2)
    private Float rate;

}
