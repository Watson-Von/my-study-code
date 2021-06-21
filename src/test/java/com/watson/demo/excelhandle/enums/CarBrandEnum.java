package com.watson.demo.excelhandle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: fengHangWen
 * @Description: 维护车辆名称和车辆code枚举
 * @Date: 2021/6/21 12:48
 **/
@Getter
@AllArgsConstructor
public enum CarBrandEnum {

  AUDI("奥迪", "AUDI"),
  BMW("宝马", "BMW"),
  PORSCHE("保时捷", "PORSCHE"),
  BENZ("奔驰", "BENZ"),
  HONDA("本田", "HONDA"),
  PEUGEOT("标致", "PEUGEOT"),
  BUICK("别克", "BUICK"),
  BENTLEY("宾利", "BENTLEY"),
  VW("大众", "VW"),
  FERRARI("法拉利", "FERRARI"),
  TOYOTA("丰田", "TOYOTA"),
  FORD("福特", "FORD"),
  JAGUAR("捷豹", "JAGUAR"),
  CADILLAC("凯迪拉克", "CADILLAC"),
  LAMBORGHINI("兰博基尼", "LAMBORGHINI"),
  ROLLSROYCE("劳斯莱斯", "ROLLS-ROYCE"),
  LEXUS("雷克萨斯", "LEXUS"),
  LINCOLN("林肯", "LINCOLN"),
  LANDROVER("路虎", "LANDROVER"),
  MAZDA("马自达", "MAZDA"),
  MASERATI("玛莎拉蒂", "MASERATI"),
  MAYBACH("迈巴赫", "MAYBACH"),
  MINI("迷你", "MINI"),
  NISSAN("日产", "NISSAN"),
  MITSUBISHI("三菱", "MITSUBISHI"),
  TESLA("特斯拉", "TESLA"),
  VOLVO("沃尔沃", "VOLVO"),
  CHEVROLET("雪佛兰", "CHEVROLET"),
  CITROEN("雪铁龙", "CITROEN"),
  INFINITI("英菲尼迪", "INFINITI"),
  HYUNDAI("现代", "HYUNDAI"),
  KIA("起亚", "KIA"),
  SUBARU("斯巴鲁", "SUBARU"),
  OTHER("其他", "BRAND_OTHER");

  private final String carBrandName;

  private final String carBrandCode;

  /**
   * @Author: fengHangWen
   * @Description: 根据车辆名称获取车辆Code
   * @Date: 2021/6/21 12:33
   **/
  public static String getCarBrandCodeByName(String carBrandName) {

    if (StringUtils.isEmpty(carBrandName)) {
      return "";
    }

    for (CarBrandEnum carBrandEnum : values()) {

      if (carBrandEnum.getCarBrandName().equalsIgnoreCase(carBrandName)) {
        return carBrandEnum.getCarBrandCode();
      }

    }

    return "";

  }


}
