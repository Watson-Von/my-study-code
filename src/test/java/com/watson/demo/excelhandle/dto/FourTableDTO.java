package com.watson.demo.excelhandle.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: fengHangWen
 * @Description: 整合四张表数据导入定义
 * @Date: 2021/6/8 9:52
 **/
@Data
public class FourTableDTO {

  /**
   * @Author: fengHangWen
   * @Description: distribute_policy_complex_type_config(分配策略条件组合类型配置表)
   * @Date: 2021/6/8 9:59
   **/
  @ExcelIgnore
  private String id1;
  @ExcelProperty(value = "配置类别(enum:COMMON_DEMAND,STOCK_DEMAND)", index = 0)
  private String configCategory;
  @ExcelProperty(value = "复杂类型", index = 1)
  private String complexType;
  @ExcelProperty(value = "优先级", index = 2)
  private String sequence;
  @ExcelProperty(value = "描述", index = 3)
  private String description;

  /**
   * @Author: fengHangWen
   * @Description: distribute_policy_complex_condition_config(分配策略条件组合详情配置表)
   * @Date: 2021/6/8 10:00
   **/
  @ExcelIgnore
  private Long id2;
  @ExcelIgnore
  private Long complexTypeConfigId;
  @ExcelProperty(value = "col1", index = 4)
  private String col1 = "";
  @ExcelProperty(value = "col2", index = 5)
  private String col2 = "";
  @ExcelProperty(value = "col3", index = 6)
  private String col3 = "";
  @ExcelProperty(value = "col4", index = 7)
  private String col4 = "";
  @ExcelProperty(value = "col5", index = 8)
  private String col5 = "";
  @ExcelProperty(value = "col6", index = 9)
  private String col6 = "";
  @ExcelProperty(value = "描述", index = 10)
  private String description2 = "";

  /**
   * @Author: fengHangWen
   * @Description: distribute_policy_config(分配策略配置表)
   * @Date: 2021/6/8 10:04
   **/
  @ExcelIgnore
  private Integer id3;
  @ExcelIgnore
  private String complexConditionConfigId;
  @ExcelIgnore
  private String type;
  @ExcelProperty(value = "标签", index = 12)
  private String label;
  @ExcelProperty(value = "总数", index = 13)
  private String count;
  @ExcelProperty(value = "优先级", index = 14)
  private String sequence2;
  @ExcelIgnore
  private Integer parentId;


  /**
   * @Author: fengHangWen
   * @Description: distribute_policy_ability_config(分配策略其他能力控制表)
   * @Date: 2021/6/8 10:06
   **/
  @ExcelIgnore
  private Long complexConditionConfigId2;
  @ExcelProperty(value = "是否系统报价遵从分流", index = 15)
  private Integer autoFollowDistributePolicy;
  @ExcelProperty(value = "是否开启本地优先", index = 16)
  private Integer localPriority;
  @ExcelProperty(value = "是否全国本地互补", index = 17)
  private Integer nationLocalComplementation;
  @ExcelProperty(value = "是否开启跨区域首轮分配召回", index = 18)
  private Integer crossRegionalFirstRecall;
  @ExcelProperty(value = "是否开启报价不满足召回", index = 19)
  private Integer crossRegionalQuoteRecall;
  @ExcelProperty(value = "本地优先最长等待时间", index = 20)
  private Integer locallPriorityWaitTime;
  @ExcelProperty(value = "周边配置类型(enum:BY_DISTRICT, BY_DISTANCE, BY_AVG_ALGORITHM)", index = 21)
  private String peripheryConfigType;
  @ExcelProperty(value = "周边配置距离值 xx公里", index = 22)
  private Integer peripheryConfig;


}
