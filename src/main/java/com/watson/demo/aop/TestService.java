package com.watson.demo.aop;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    @GridLock
    public void test(String grid){

        System.out.println(grid);

    }

}
