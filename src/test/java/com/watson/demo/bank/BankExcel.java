package com.watson.demo.bank;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankExcel {

    @ExcelProperty(value = "银行", index = 0)
    private String bankName;

    @ExcelProperty(value = "省份", index = 1)
    private String provinceName;

    @ExcelProperty(value = "城市", index = 2)
    private String cityName;

    @ExcelProperty(value = "swift code", index = 3)
    private String swiftCode;

    @ExcelProperty(value = "code", index = 4)
    private String code;

}
