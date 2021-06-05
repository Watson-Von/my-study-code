package com.watson.demo.excel.handle;

import com.alibaba.excel.EasyExcel;
import com.watson.demo.excel.datehandle.dto.SupplyAdvantageConfigDTO;
import com.watson.demo.excel.datehandle.dto.SupplyPreferenceConfigDTO;
import com.watson.demo.excel.datehandle.dto.SupplySConfigDTO;
import com.watson.demo.excel.datehandle.listener.SupplyAdvantageConfigDTOListener;
import com.watson.demo.excel.datehandle.listener.SupplyPreferenceConfigListener;
import com.watson.demo.excel.datehandle.listener.SupplySConfigListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
//@SpringBootTest(classes = DemoApplication.class)
//@RunWith(SpringRunner.class)
public class ExcelDataImportTest {


    /**
     * @Author: fengHangWen
     * @Description: 优势商家配置
     * @Date: 2021/6/2 16:33
     **/
    @Test
    public void test3() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\数据\\智能分流二期1阶段临时测试数据2021-6-4(3).xlsx";

        EasyExcel.read(excelFilePath, SupplyAdvantageConfigDTO.class,
                new SupplyAdvantageConfigDTOListener()).sheet("优势商家 (龙坂) (2)").doRead();

    }

    /**
     * @Author: fengHangWen
     * @Description: 喜好商家配置
     * @Date: 2021/6/2 16:33
     **/
    @Test
    public void test2() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\数据\\智能分流二期1阶段临时测试数据2021-6-4.xlsx";

        EasyExcel.read(excelFilePath, SupplyPreferenceConfigDTO.class,
                new SupplyPreferenceConfigListener()).sheet("维修厂个性化喜好").doRead();

    }

    /**
     * @Author: fengHangWen
     * @Description: s商家配置
     * @Date: 2021/6/2 16:34
     **/
    @Test
    public void test1() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\数据\\智能分流二期1阶段临时测试数据2021-6-4.xlsx";

        EasyExcel.read(excelFilePath, SupplySConfigDTO.class,
                new SupplySConfigListener()).sheet("S商家").doRead();

    }


}
