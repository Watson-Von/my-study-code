package com.watson.demo.shareuserinfo.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EnableUserInfoTransmitterAutoConfiguration.class})
public @interface EnableUserInfoTransmitter {
}
