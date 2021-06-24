package com.watson.demo;

import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursionTest {

    @Test
    public void test() {

        List<Person> personList = new ArrayList<>();

        Person thirdFather = new Person();
        thirdFather.setAge(3);
        thirdFather.setName("冯行文3");

        Person secondFather = new Person();
        secondFather.setAge(2);
        secondFather.setName("冯行文2");
        secondFather.setFather(thirdFather);

        Person firstFather = new Person();
        firstFather.setAge(1);
        firstFather.setName("冯行文1");
        firstFather.setFather(secondFather);

        personList.add(firstFather);

        Person bFather = new Person();
        bFather.setAge(2);
        bFather.setName("冯行文B");

        Person aFather = new Person();
        firstFather.setAge(1);
        firstFather.setName("冯行文A");
        aFather.setFather(bFather);

        personList.add(aFather);

        Person one = new Person();
        one.setAge(1);
        one.setName("one");

        personList.add(one);

        Map<String, String> strMap = new HashMap<>();
        for (Person person : personList) {
            recursion(person, strMap);
        }

        System.out.println(strMap);
    }

    private void recursion(Person person, Map<String, String> strMap) {

        // 结束条件
        if (person.getFather() == null) {
            strMap.put(person.getName(), person.getAge().toString());
            return;
        }

        recursion(person.getFather(), strMap);
    }

    @Data
    static class Person {
        String name;
        Integer age;
        Person father;
    }

}
