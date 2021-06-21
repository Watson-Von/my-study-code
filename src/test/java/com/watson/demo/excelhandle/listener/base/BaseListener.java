package com.watson.demo.excelhandle.listener.base;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.util.SqlUtils;
import com.casstime.ec.cloud.inquiry.infrastructure.handler.excelhandle.util.SqlUtils.ValueType;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: fengHangWen
 * @Description: 解析excel 基础监听器
 * @Date: 2021/6/18 18:08
 **/
@Slf4j
public abstract class BaseListener<T> extends AnalysisEventListener<T> {

  private final List<T> excelDataList = new ArrayList<>();

  private final String SQL_FILE_ROOT = "C:\\Users\\Administrator\\Desktop\\Aston49\\";

  // excel 的sheet, 下标从 0 开始
  private Integer sheetNo;

  // excel 的sheet名称
  private String sheetName;

  // 表名
  private String tableName;

  // 列名
  private List<String> counlumList;

  // k:表名; v:列名
  private Map<String, List<String>> map = new HashMap<>();

  /**
   * @Author: fengHangWen
   * @Description: 用于构造基于 sheetNo 的多表数据; map 中的key: tableName, value: 列名集合
   * @Date: 2021/6/18 18:21
   **/
  public BaseListener(
      String excelFilePath,
      Integer sheetNo,
      Map<String, List<String>> map) {
    this.sheetNo = sheetNo;
    this.map = map;

    // 泛型实例化操作
    Class<T> clazz = getTClass();
    EasyExcel.read(excelFilePath, clazz, this).sheet(sheetNo).doRead();
  }

  /**
   * @Author: fengHangWen
   * @Description: 用于构造基于 sheetName 的多表数据; map 中的key: tableName, value: 列名集合
   * @Date: 2021/6/18 18:20
   **/
  public BaseListener(
      String excelFilePath,
      String sheetName,
      Map<String, List<String>> map) {
    this.sheetName = sheetName;
    this.map = map;

    // 泛型实例化操作
    Class<T> clazz = getTClass();
    EasyExcel.read(excelFilePath, clazz, this).sheet(sheetName).doRead();
  }

  /**
   * @Author: fengHangWen
   * @Description: 用于构造基于 sheetNo 的单表数据
   * @Date: 2021/6/18 18:20
   **/
  public BaseListener(
      String excelFilePath,
      Integer sheetNo,
      String tableName,
      List<String> counlumList) {
    this.sheetNo = sheetNo;
    this.tableName = tableName;
    this.counlumList = counlumList;

    // 泛型实例化操作
    Class<T> clazz = getTClass();
    EasyExcel.read(excelFilePath, clazz, this).sheet(sheetNo).doRead();
  }

  /**
   * @Author: fengHangWen
   * @Description: 用于构造基于 sheetName 的单表数据
   * @Date: 2021/6/18 18:19
   **/
  public BaseListener(
      String excelFilePath,
      String sheetName,
      String tableName,
      List<String> counlumList) {
    this.sheetName = sheetName;
    this.tableName = tableName;
    this.counlumList = counlumList;

    // 泛型实例化操作
    Class<T> clazz = getTClass();
    EasyExcel.read(excelFilePath, clazz, this).sheet(sheetName).doRead();
  }

  /**
   * @Author: fengHangWen
   * @Description: 泛型实例化操作, 解决泛型涂擦导致的泛型无法实例化的问题
   * @Date: 2021/6/18 16:06
   **/
  private Class<T> getTClass() {
    Type superclass = getClass().getGenericSuperclass();
    Class<T> clazz = null;
    if (superclass instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) superclass;
      Type[] typeArray = parameterizedType.getActualTypeArguments();
      if (typeArray != null && typeArray.length > 0) {
        clazz = (Class<T>) typeArray[0];
      }
    }
    return clazz;
  }

  /**
   * @Author: fengHangWen
   * @Description: 收集 excel 解析数据
   * @Date: 2021/6/18 15:33
   **/
  @Override
  public void invoke(T data, AnalysisContext context) {
    excelDataList.add(data);
  }

  /**
   * @Author: fengHangWen
   * @Description: 获取 excel 解析数据
   * @Date: 2021/6/18 15:32
   **/
  protected List<T> getExcelDataList() {
    return excelDataList;
  }

  /**
   * @Author: fengHangWen
   * @Description: 打印 insert sql 脚本
   * @Date: 2021/6/18 15:32
   **/
  protected void printInsertSqlScript(List<List<SqlUtils.ValueType>> insertDataList) {

    log.info("================================所有数据解析完成=================================");

    log.info("一共解析到sheet: {}: {} 条数据", StringUtils.isEmpty(sheetName) ? sheetNo : sheetName,
        getExcelDataList().size());

    if (CollectionUtils.isEmpty(insertDataList)) {
      return;
    }

    // 生成的脚本按照 1000 分批导出, 为了满足sql 脚本提审的需要
    List<List<List<ValueType>>> partition = Lists.partition(insertDataList, 1000);

    for (int i = 0; i < partition.size(); i++) {

      // sql 脚本数据

      String result = SqlUtils.createInsertSqlScrip(tableName, counlumList, partition.get(i));

      // sql脚本名称
      String sqlScripName = tableName + "-" + i + ".sql";

      // 生成sql 脚本文件
      generateSqlScriptFile(result, sqlScripName);

    }

  }

  /**
   * @Author: fengHangWen
   * @Description: 生成sql 脚本文件
   * @Date: 2021/6/21 19:04
   **/
  private void generateSqlScriptFile(String result, String sqlFileName) {

    if (StringUtils.isEmpty(result) || StringUtils.isEmpty(sqlFileName)) {
      throw new IllegalArgumentException("脚本数据或脚本文件名称不能为空");
    }

    File f = new File(SQL_FILE_ROOT + sqlFileName);
    FileOutputStream fos = null;
    OutputStreamWriter dos = null;
    try {
      fos = new FileOutputStream(f);
      dos = new OutputStreamWriter(fos);
      dos.write(result);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (dos != null) {
          dos.close();
        }
        if (fos != null) {
          fos.close();
        }
        // 如果打印数据量太大, 控制台会吞掉打印数据, 解决办法:
        // 1. 在idea安装路径的 bin 目录下找到配置文件 idea.properties,
        // 2. 将 idea.cycle.buffer.size=1024 设置成 idea.cycle.buffer.size=disabled
        // 3. 重启 idea
        log.info("\r\n{}", result);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * @Author: fengHangWen
   * @Description: 根据 tableName 从 map中获取列数据
   * @Date: 2021/6/18 17:20
   **/
  protected void printInsertSqlScript(
      String tableName,
      List<List<SqlUtils.ValueType>> insertDataList) {

    log.info("\r\n");
    log.info("\r\n");
    log.info("一共解析到 {}: {} 条数据", tableName, insertDataList.size());

    List<String> counlumList = map.getOrDefault(tableName, new ArrayList<>());

    if (CollectionUtils.isEmpty(counlumList)) {
      log.error("获取不到列数据, 请检查 tableName: {} 是否正确", tableName);
      return;
    }

    // 如果打印数据量太大, 控制台会吞掉打印数据, 解决办法:
    // 1. 在idea安装路径的 bin 目录下找到配置文件 idea.properties,
    // 2. 将 idea.cycle.buffer.size=1024 设置成 idea.cycle.buffer.size=disabled
    // 3. 重启 idea
    // 生成的脚本按照 1000 分批导出, 为了满足sql 脚本提审的需要
    List<List<List<ValueType>>> partition = Lists.partition(insertDataList, 1000);

    for (int i = 0; i < partition.size(); i++) {

      // sql 脚本数据
      String result = SqlUtils.createInsertSqlScrip(tableName, counlumList, partition.get(i));

      // sql 脚本名称
      String sqlScripName = tableName + "-" + i + ".sql";

      // 生成脚本
      generateSqlScriptFile(result, sqlScripName);

    }

  }

  public static void main(String[] args) {

    // 类型涂擦问题
    List<String> list1 = new ArrayList<String>();
    list1.add("abc");

    List<Integer> list2 = new ArrayList<Integer>();
    list2.add(123);

    // 在生成的字节码中是不包含泛型中的类型信息的,
    // 使用泛型的时候加上类型参数, 在编译器编译的时候会去掉, 这个过程成为类型擦除。
    // JVM看到的只是List, 所以是 true
    System.out.println(list1.getClass() == list2.getClass());

  }

}
