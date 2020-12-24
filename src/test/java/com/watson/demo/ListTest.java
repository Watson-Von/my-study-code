package com.watson.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListTest {

    public static void main(String[] args) {

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
class User {
    private String name;
    private int age;
}
