package com.watson.demo.shareuserinfo.config;

import com.watson.demo.shareuserinfo.filter.TransmitUserInfoFilter;
import com.watson.demo.shareuserinfo.intercepter.TransmitUserInfoFeighClientIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnableUserInfoTransmitterAutoConfiguration {

    public EnableUserInfoTransmitterAutoConfiguration() {
    }

    @Bean
    public TransmitUserInfoFeighClientIntercepter transmitUserInfo2FeighHttpHeader() {
        System.out.println("-----TransmitUserInfoFeighClientInterceptor");
        return new TransmitUserInfoFeighClientIntercepter();
    }

    @Bean
    public TransmitUserInfoFilter transmitUserInfoFromHttpHeader() {
        System.out.println("-----TransmitUserInfoFilter");
        return new TransmitUserInfoFilter();
    }

}
