package com.watson.demo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.util.List;


public class EasyExcelUtil {

    /**
     * @param : fileName文件路径
     * @param : clazz导出数据类型
     * @param : list导出数据
     * @author : fengHangWen
     * @version : 1.0
     * @description :
     * @company : 深圳一点盐光科技有限公司
     * @date : 2020/12/1 17:57
     */
    public static <T> void writeToExcel(String fileName, Class<T> clazz, List<T> list) {
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        WriteSheet sheet = EasyExcel.writerSheet().head(clazz).build();
        excelWriter.write(list, sheet);
        excelWriter.finish();
    }

}
