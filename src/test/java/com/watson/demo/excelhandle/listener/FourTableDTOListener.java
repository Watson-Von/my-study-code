package com.watson.demo.excelhandle.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.casstime.commons.utils.JsonUtil;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.dto.FourTableDTO;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.enums.CarBrandEnum;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.enums.LocationIdEnum;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.listener.base.BaseListener;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.util.SqlUtils;
import com.casstime.ec.cloud.uniqueId.UniqueIdUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class FourTableDTOListener extends BaseListener<FourTableDTO> {

  public FourTableDTOListener(
      String excelFilePath,
      String sheetName,
      Map<String, List<String>> map) {
    super(excelFilePath, sheetName, map);
  }

  private static final String DATA_CREATOR = "system";

  private static final String separation = "__";

  private static final Map<String, Long> map = Maps.newHashMap();

  private final static List<DistributePolicyComplexTypeConfigDO> distributePolicyComplexTypeConfigDOList = new ArrayList<>();
  private final static List<DistributePolicyComplexConditionConfig> distributePolicyComplexConditionConfigList = new ArrayList<>();
  private final static List<DistributePolicyConfig> distributePolicyConfigList = new ArrayList<>();
  private final static List<DistributePolicyAbilityConfig> distributePolicyAbilityConfigList = new ArrayList<>();

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {

    // 第一次分组, 根据 configCategory + complexType + sequence + description 分组
    // 得到 distribute_policy_complex_type_config 表的数据
    Map<String, List<FourTableDTO>> g1 = getExcelDataList().stream()
        .collect(Collectors.groupingBy(fourTableDTO ->
            fourTableDTO.getConfigCategory()
                + separation + fourTableDTO.getComplexType()
                + separation + fourTableDTO.getSequence()
                + separation + fourTableDTO.getDescription()));

    g1.forEach((t1, list1) -> {

      String[] strArray1 = t1.split(separation);
      long complexTypeConfigId = UniqueIdUtil.acquireLongId();

      distributePolicyComplexTypeConfigDOList.add(createT1Data(complexTypeConfigId, strArray1));

      // 第二次分组, 根据 col1 + col2 + col3 + col4 + col5 + col6 + description2 分组
      // 得到 distribute_policy_complex_condition_config 表的数据
      Map<String, List<FourTableDTO>> g2 = list1.stream()
          .collect(Collectors.groupingBy(fourTableDTO ->
              fourTableDTO.getCol1()
                  + separation + fourTableDTO.getCol2()
                  + separation + fourTableDTO.getCol3()
                  + separation + fourTableDTO.getCol4()
                  + separation + fourTableDTO.getCol5()
                  + separation + fourTableDTO.getCol6()
                  + separation + fourTableDTO.getDescription2()));

      g2.forEach((t2, list2) -> {

        String[] strArray2 = t2.split(separation);
        long complexConditionConfigId = UniqueIdUtil.acquireLongId();

        distributePolicyComplexConditionConfigList
            .add(createT2Data(complexConditionConfigId, complexTypeConfigId, strArray2));

        list2.forEach(fourTableDTO -> distributePolicyConfigList
            .add(createT3Data(complexConditionConfigId, fourTableDTO)));

        map.clear();

        FourTableDTO fourTableDTO = list2.get(0);
        distributePolicyAbilityConfigList.add(new DistributePolicyAbilityConfig(
            complexConditionConfigId,
            fourTableDTO.getAutoFollowDistributePolicy(),
            fourTableDTO.getLocalPriority(),
            fourTableDTO.getNationLocalComplementation(),
            fourTableDTO.getCrossRegionalFirstRecall(),
            fourTableDTO.getCrossRegionalQuoteRecall(),
            fourTableDTO.getLocallPriorityWaitTime(),
            fourTableDTO.getPeripheryConfigType(),
            fourTableDTO.getPeripheryConfig()
        ));
      });

    });

    printInsertSqlScript1();
    printInsertSqlScript2();
    printInsertSqlScript3();
    printInsertSqlScript4();

  }

  private void printInsertSqlScript4() {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();
    for (DistributePolicyAbilityConfig dto : FourTableDTOListener.distributePolicyAbilityConfigList) {

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getComplexConditionConfigId()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getAutoFollowDistributePolicy()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getLocalPriority()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getNationLocalComplementation()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getCrossRegionalFirstRecall()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getCrossRegionalQuoteRecall()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getLocalPriorityWaitTime()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getPeripheryConfigType()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getPeripheryConfigValue()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR))
      );

    }

    super.printInsertSqlScript("distribute_policy_ability_config", insertDataList);

  }

  private void printInsertSqlScript3() {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();
    for (DistributePolicyConfig dto : FourTableDTOListener.distributePolicyConfigList) {

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getId()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getComplexConditionConfigId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getType()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getLabel()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getCount()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getSequence()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getParentId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR))
      );

    }

    super.printInsertSqlScript("distribute_policy_config", insertDataList);

  }

  private void printInsertSqlScript2() {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();
    for (DistributePolicyComplexConditionConfig dto : FourTableDTOListener.distributePolicyComplexConditionConfigList) {

      String col3 = dto.getCol3();
      // 如果拿到车辆品牌Code, 则设置到 col3上
      String carBrandCode = CarBrandEnum.getCarBrandCodeByName(col3);
      if (StringUtils.isNotEmpty(carBrandCode)) {
        dto.setCol3(carBrandCode);
      }

      String col4 = dto.getCol4();
      // 如果拿到主机厂ID, 则设置到 col4上
      String locationId = LocationIdEnum.getLocationIdByName(col4);
      if (StringUtils.isNotEmpty(locationId)) {
        dto.setCol4(locationId);
      }

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getId()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getComplexTypeConfigId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCol1()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCol2()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCol3()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCol4()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCol5()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getCol6()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getDescription()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR))
      );

    }

    super.printInsertSqlScript("distribute_policy_complex_condition_config", insertDataList);

  }

  private void printInsertSqlScript1() {

    List<List<SqlUtils.ValueType>> insertDataList = Lists.newArrayList();

    for (DistributePolicyComplexTypeConfigDO dto : FourTableDTOListener.distributePolicyComplexTypeConfigDOList) {

      // 这里添加数据的顺序需要与列名的顺序保持一致
      insertDataList.add(Lists.newArrayList(
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getId()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getConfigCategory()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getComplexType()),
          new SqlUtils.ValueType(SqlUtils.NUMBER_TYPE, dto.getSequence()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, dto.getDescription()),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR),
          new SqlUtils.ValueType(SqlUtils.STRING_TYPE, DATA_CREATOR))
      );

    }

    super.printInsertSqlScript("distribute_policy_complex_type_config", insertDataList);

  }

  /**
   * @Author: fengHangWen
   * @Description: 构造 distribute_policy_config 表数据
   * @Date: 2021/6/8 14:56
   **/
  private DistributePolicyConfig createT3Data(long id2, FourTableDTO fourTableDTO) {

    if (fourTableDTO == null) {
      return null;
    }

    long id = UniqueIdUtil.acquireLongId();
    Long parentId = null;
    String label = fourTableDTO.getLabel();

    if ("LOCAL".equals(label)) {
      map.put("LOCAL_parentId", id);
    } else if ("NATION".equals(label)) {
      map.put("NATION_parentId", id);
    } else if (label.contains("LOCAL")) {
      parentId = map.getOrDefault("LOCAL_parentId", null);
    } else if (label.contains("NATION")) {
      parentId = map.getOrDefault("NATION_parentId", null);
    } else {
      log.warn("label 无法判断 : " + JsonUtil.serializer(fourTableDTO));
    }

    // 如果 sequence2 等于 null, 则赋值 0
    Integer sequence = StringUtils.isEmpty(fourTableDTO.getSequence2()) ? 0
        : Integer.parseInt(fourTableDTO.getSequence2());

    return new DistributePolicyConfig(id, id2,
        "FUR", fourTableDTO.getLabel(), Integer.parseInt(fourTableDTO.getCount()),
        sequence, parentId);
  }

  /**
   * @Author: fengHangWen
   * @Description: 构造 distribute_policy_complex_condition_config 表数据
   * @Date: 2021/6/8 14:28
   **/
  private DistributePolicyComplexConditionConfig createT2Data(
      long complexConditionConfigId,
      long id1,
      String[] strArray) {

    String col1 = strArray[0];
    String col2 = strArray[1];
    String col3 = strArray[2];
    String col4 = strArray[3];
    String col5 = strArray[4];
    String col6 = strArray[5];
    String description = strArray[6];

    return new DistributePolicyComplexConditionConfig(
        complexConditionConfigId, id1, col1, col2, col3, col4, col5, col6, description);
  }

  /**
   * @Author: fengHangWen
   * @Description: 构造 distribute_policy_complex_type_config 表数据
   * @Date: 2021/6/8 14:18
   **/
  private static DistributePolicyComplexTypeConfigDO createT1Data(long id, String[] strArray) {
    String configCategory = strArray[0];
    String complexType = strArray[1];
    String sequence = strArray[2];
    String description = strArray[3];
    return new DistributePolicyComplexTypeConfigDO(id, configCategory, complexType,
        Integer.parseInt(sequence), description);
  }

  /**
   * @Author: fengHangWen
   * @Description: 分配策略条件组合类型配置表 DO
   * @Date: 2021/6/8 14:01
   **/
  @Data
  @AllArgsConstructor
  private static class DistributePolicyComplexTypeConfigDO {

    private Long id;
    private String configCategory;
    private String complexType;
    private Integer sequence;
    private String description;

  }

  /**
   * @Author: fengHangWen
   * @Description: 分配策略条件组合详情配置表 DO
   * @Date: 2021/6/8 14:02
   **/
  @Data
  @AllArgsConstructor
  private static class DistributePolicyComplexConditionConfig {

    private Long id;
    private Long complexTypeConfigId;
    private String col1;
    private String col2;
    private String col3;
    private String col4;
    private String col5;
    private String col6;
    private String description;

  }

  /**
   * @Author: fengHangWen
   * @Description: 分配策略配置表 DO
   * @Date: 2021/6/8 14:03
   **/
  @Data
  @AllArgsConstructor
  private static class DistributePolicyConfig {

    private Long id;
    private Long complexConditionConfigId;
    private String type;
    private String label;
    private Integer count;
    private Integer sequence;
    private Long parentId;

  }

  /**
   * @Author: fengHangWen
   * @Description: 分配策略其他能力控制表 DO
   * @Date: 2021/6/8 14:05
   **/
  @Data
  @AllArgsConstructor
  private static class DistributePolicyAbilityConfig {

    private Long complexConditionConfigId;
    private Integer autoFollowDistributePolicy;
    private Integer localPriority;
    private Integer nationLocalComplementation;
    private Integer crossRegionalFirstRecall;
    private Integer crossRegionalQuoteRecall;
    private Integer localPriorityWaitTime;
    private String peripheryConfigType;
    private Integer peripheryConfigValue;

  }


}
