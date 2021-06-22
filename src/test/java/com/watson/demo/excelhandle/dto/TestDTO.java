package com.watson.demo.excelhandle.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class TestDTO {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private String age;

    @ExcelProperty(value = "护照", index = 2)
    private String passport;

}
