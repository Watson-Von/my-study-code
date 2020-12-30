package com.watson.demo.bank;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalBankExcel {

    @ExcelProperty(value = "地方银行", index = 0)
    private String bankName;

    @ExcelProperty(value = "城市", index = 1)
    private String cityName;

    @ExcelProperty(value = "swift code", index = 2)
    private String swiftCode;

    @ExcelProperty(value = "code", index = 3)
    private String code;

}
