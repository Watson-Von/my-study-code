package com.watson.demo.aop;

import com.watson.demo.redis.RedisUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class GridLockAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.watson.demo.aop.GridLock)")
    public void annotationPoinCut() {
    }

//    @Around("annotationPoinCut()")
//    public void around(ProceedingJoinPoint pjp) throws Throwable {
//
//        System.out.println("开始执行 around1 .....");
//        System.out.println("拿到的参数是: " + pjp.getArgs()[0]);
//
////        if ("SZ001".equals(pjp.getArgs()[0])) {
////
////        }
//
//        try {
//            pjp.proceed();
//        } catch (Exception e) {
//            log.error("报错了:", e);
//        }
//
//
//        System.out.println("开始执行 around2 .....");
//
//
//    }


    @Before("annotationPoinCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Object[] args = joinPoint.getArgs();

        if (redisUtil.exists("key")) {
            redisUtil.remove("key");
        } else {
            redisUtil.set("key","SZ001");
        }


        System.out.println("before .....");
    }

    @After("annotationPoinCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("after .....");
        if (redisUtil.exists("key")) {
            redisUtil.remove("key");
        }
    }


}
