package com.watson.demo.excelhandle.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: fengHangWen
 * @Description: 维护主机厂名称和ID枚举
 * @Date: 2021/6/21 15:18
 **/
@AllArgsConstructor
@Getter
public enum LocationIdEnum {

  BENZ_BEIJING("北京奔驰", "1"),
  BENZ_IMPORT("进口奔驰", "2"),
  BENZ_FUJIAN("福建奔驰", "3"),
  HONDA_DONGFENG("东风本田", "1"),
  HONDA_GUANGQI("广汽本田", "2"),
  HONDA_IMPORT("进口本田", "3"),
  NISSAN_DONGFENG("东风日产", "1"),
  NISSAN_IMPORT("进口日产", "2"),
  NISSAN_ZHENGZHOU("郑州日产", "3"),
  VW_IMPORT("进口大众", "1"),
  VW_YIQI("一汽大众", "2"),
  VW_SHANGQI("上汽大众", "3"),
  BMW_HUACHENBAOMA("华晨宝马", "1"),
  BMW_IMPORT("进口宝马", "2"),
  LOCATION_OTHER("其他", "LOCATION_OTHER");

  private final String locationName;

  private final String locationId;

  /**
   * @Author: fengHangWen
   * @Description: 根据主机厂名称获取主机厂ID
   * @Date: 2021/6/21 14:36
   **/
  public static String getLocationIdByName(String locationName) {

    if (StringUtils.isEmpty(locationName)) {
      return "";
    }

    for (LocationIdEnum locationIdEnum : values()) {

      if (locationIdEnum.getLocationName().equalsIgnoreCase(locationName)) {
        return locationIdEnum.getLocationId();
      }

    }

    return "";

  }

}
