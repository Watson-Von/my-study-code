package com.watson.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ListTest {

    @Test
    public void test3() throws ParseException {

        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<DistributionDataVO> list = new ArrayList<>();
        list.add(new DistributionDataVO("1", "冯", "131", "产品1", "1", sb.parse("2021-01-01 10:00:00"), "2021-01-01 10:00:00"));
        list.add(new DistributionDataVO("2", "行", "132", "产品2", "2", sb.parse("2021-01-01 11:00:00"), "2021-01-01 11:00:00"));
        list.add(new DistributionDataVO("3", "文", "133", "产品3", "3", sb.parse("2021-01-01 12:00:00"), "2021-01-01 12:00:00"));
        list.add(new DistributionDataVO("4", "是", "134", "产品4", "4", sb.parse("2021-01-02 10:01:00"), "2021-01-02 10:01:00"));
        list.add(new DistributionDataVO("5", "个", "135", "产品5", "5", sb.parse("2021-01-02 11:03:00"), "2021-01-02 11:03:00"));
        list.add(new DistributionDataVO("6", "人", "136", "产品6", "6", sb.parse("2021-01-03 10:00:00"), "2021-01-03 10:00:00"));

        List<DistributionDataVO> createTimesortList =
                list.stream().sorted(Comparator.comparing(DistributionDataVO::getCreateTime)).collect(Collectors.toList());

        List<DistributionDataVO> resultList = new ArrayList<>();

        cycleFilterDataBy24house(createTimesortList, resultList);

        resultList.forEach(distributionDataVO ->
                System.out.println(distributionDataVO.getId() + " -----> " + distributionDataVO.getFormatCreateTime()));

    }

    private void cycleFilterDataBy24house(List<DistributionDataVO> list,
                                          List<DistributionDataVO> resultList) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        if (list.size() == 1) {
            resultList.add(list.get(0));
            return;
        }

        // 获取到最小日期的数据
        DistributionDataVO minCreateTimeData = list.get(0);
        resultList.add(minCreateTimeData);

        Date minCreateTime = minCreateTimeData.getCreateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(minCreateTime);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        // 获取最小日期明天的日期
        Date minCreateTime_tomorrow = calendar.getTime();

        // 过滤出比 minCreateTime_tomorrow 时间大的数据
        List<DistributionDataVO> filteData = list.stream()
                .filter(distributionDataVO -> distributionDataVO.getCreateTime().compareTo(minCreateTime_tomorrow) > 0)
                .collect(Collectors.toList());

        cycleFilterDataBy24house(filteData, resultList);
    }


    @Test
    public void test2() {
        List<String> strList = new ArrayList<>();
        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");

        List<String> strList2 = strList.stream().filter(s -> s.equals("a")).collect(Collectors.toList());
        System.out.println(strList);
        System.out.println(strList2);
    }

    @Test
    public void test() {
        List<User> userList1 = new ArrayList<>();
        User a = new User("a", 1);
        User b = new User("b", 2);
        User c = new User("c", 3);
        userList1.add(a);
        userList1.add(b);
        userList1.add(c);
        List<User> userList2 = new ArrayList<>();
        User a1 = new User("a", 4);
        User e = new User("e", 5);
        User f = new User("f", 6);
        userList2.add(a1);
        userList2.add(e);
        userList2.add(f);

        List reduce2 = userList1.stream().filter(item -> !userList2.contains(item)).collect(Collectors.toList());

        System.out.println(reduce2);
    }


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class DistributionDataVO {

    private String id;
    private String name;
    private String phone;
    private String productName;
    private String gmv;
    private Date createTime;
    private String formatCreateTime;

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private String name;
    private int age;
}
