package com.watson.demo.api.version.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: fengHangWen
 * @Description: 自定义api注解, 用于版本控制
 * @Date: 2021/6/19 13:59
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface APIVersion {

    String[] value();

}
