package com.watson.demo.excelhandle;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.watson.demo.excelhandle.dto.TestDTO;
import com.watson.demo.excelhandle.listener.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;


@Slf4j
// @SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class ExcelDataImportTest {

    @Test
    public void test6() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\test.xlsx";
        ExcelReader excelReader = EasyExcel.read(excelFilePath).build();

        ReadSheet readSheet1 =
                EasyExcel.readSheet("b").head(TestDTO.class).registerReadListener(new TestDTOListener()).build();

        ReadSheet readSheet3 =
                EasyExcel.readSheet("c").head(TestDTO.class).registerReadListener(new TestDTOListener()).build();

        ReadSheet readSheet2 =
                EasyExcel.readSheet("a").head(TestDTO.class).registerReadListener(new TestDTOListener()).build();

        // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
        excelReader.read(readSheet3, readSheet2, readSheet1);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();

    }

    @Test
    public void test5() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\Aston49\\数据\\网格供应力-龙华坂田和深圳北最终.xlsx";

        new ExcellentGridDeliverabilityConfigDTOListener(
                excelFilePath,
                "网格供应力-深圳北",
                "excellent_grid_deliverability_config",
                Lists.newArrayList(
                        "market_grid_code",
                        "car_brand_id", "location_id",
                        "car_level", "business_scenario",
                        "business_category_name", "supply_source_geo_id",
                        "supply_source_geo_type", "supply_source_name",
                        "supply_source_level", "supply_source_area",
                        "created_by", "created_stamp",
                        "last_updated_by", "last_updated_stamp"));

    }

    @Test
    public void test4() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\Aston49\\数据\\分配策略-龙华坂田和深圳北.xlsx";

        Map<String, List<String>> map = Maps.newHashMap();
        String distribute_policy_complex_type_config_table = "distribute_policy_complex_type_config";
        List<String> distribute_policy_complex_type_config_table_counlum_list =
                Lists.newArrayList(
                        "id",
                        "config_category", "complex_type",
                        "sequence", "description",
                        "created_by", "last_updated_by");
        map.put(distribute_policy_complex_type_config_table,
                distribute_policy_complex_type_config_table_counlum_list);

        String distribute_policy_complex_condition_config_table = "distribute_policy_complex_condition_config";
        List<String> distribute_policy_complex_condition_config_table_counlum_list =
                Lists.newArrayList(
                        "id",
                        "complex_type_config_id", "col1",
                        "col2", "col3",
                        "col4", "col5",
                        "col6", "description",
                        "created_by", "last_updated_by");
        map.put(distribute_policy_complex_condition_config_table,
                distribute_policy_complex_condition_config_table_counlum_list);

        String distribute_policy_config_table = "distribute_policy_config";
        List<String> distribute_policy_config_table_counlum_list =
                Lists.newArrayList(
                        "id",
                        "complex_condition_config_id", "type",
                        "label", "count",
                        "sequence", "parent_id",
                        "created_by", "last_updated_by");
        map.put(distribute_policy_config_table, distribute_policy_config_table_counlum_list);

        String distribute_policy_ability_config_table = "distribute_policy_ability_config";
        List<String> distribute_policy_ability_config_table_counlum_list =
                Lists.newArrayList(
                        "complex_condition_config_id",
                        "is_auto_follow_distribute_policy", "is_local_priority",
                        "is_nation_local_complementation", "is_cross_regional_first_recall",
                        "is_cross_regional_quote_recall", "local_priority_wait_time",
                        "periphery_config_type", "periphery_config_value",
                        "created_by", "last_updated_by");
        map.put(distribute_policy_ability_config_table,
                distribute_policy_ability_config_table_counlum_list);

        new FourTableDTOListener(excelFilePath, "SZ001分配策略final", map);

    }


    /**
     * @Author: fengHangWen
     * @Description: 优势商家配置
     * @Date: 2021/6/2 16:33
     **/
    @Test
    public void test3() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\Aston49\\数据\\龙华坂田和深圳北优势商家-正式环境数据.xlsx";

        new SupplyAdvantageConfigDTOListener(
                excelFilePath,
                "龙华坂田深圳北优势商家",
                "supply_advantage_config",
                Lists.newArrayList(
                        "market_grid_code",
                        "car_brand_id",
                        "location_id",
                        "business_category_name",
                        "supply_type",
                        "store_id",
                        "description",
                        "created_by",
                        "last_updated_by"));

    }

    /**
     * @Author: fengHangWen
     * @Description: 喜好商家配置
     * @Date: 2021/6/2 16:33
     **/
    @Test
    public void test2() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\Aston49\\数据\\龙华坂田及深圳北维修厂喜欢-正式环境数据.xlsx";

        new SupplyPreferenceConfigListener(
                excelFilePath,
                "正式环境",
                "supply_preference_config",
                Lists.newArrayList(
                        "garage_company_id",
                        "car_brand_id",
                        "business_category_name",
                        "store_id",
                        "description",
                        "created_by",
                        "last_updated_by")
        );

    }

    /**
     * @Author: fengHangWen
     * @Description: s商家配置
     * @Date: 2021/6/2 16:34
     **/
    @Test
    public void test1() {

        String excelFilePath = "C:\\Users\\Administrator\\Desktop\\Aston49\\数据\\S商家-研发_正式.xlsx";

        new SupplySConfigListener(excelFilePath,
                "S商家",
                "supply_s_config",
                Lists.newArrayList(
                        "market_grid_code",
                        "store_id", "store_name",
                        "created_by", "last_updated_by")
        );

    }


}
