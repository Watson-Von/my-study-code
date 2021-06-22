package com.watson.demo.excelhandle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.watson.demo.excelhandle.dto.TestDTO;
import com.watson.demo.excelhandle.listener.base.BaseListener;

import java.util.List;

public class TestDTOListener extends BaseListener<TestDTO> {

    @Override
    public void doAfterAllAnalysed(List<TestDTO> list) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

        List<TestDTO> excelDataList = getExcelDataList();
        System.out.println(excelDataList);

    }

}
