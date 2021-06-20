package com.watson.demo.api.version.config;

import com.watson.demo.api.version.annotation.APIVersion;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @Author: fengHangWen
 * @Description:
 * @Date: 2021/6/19 14:31
 **/
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {


    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    /**
     * @Author: fengHangWen
     * @Description: 从 @APIVersion 自定义注解中读取版本信息,
     * 拼接上原有的、不带版本号的 URL Pattern,
     * 构成新的 RequestMappingInfo, 来通过注解的方式为接口增加基于 URL 的版本号
     * @Date: 2021/6/19 14:06
     **/
    @Override
    protected void registerHandlerMethod(Object handler,
                                         Method method,
                                         RequestMappingInfo mapping) {

        Class<?> controllerClass = method.getDeclaringClass();
        // 类上的 APIVersion 注解
        APIVersion apiVersion = AnnotationUtils.findAnnotation(controllerClass, APIVersion.class);
        // 方法上的 APIVersion 注解
        APIVersion methodAnnotation = AnnotationUtils.findAnnotation(method, APIVersion.class);
        // 以方法上的注解优先
        if (methodAnnotation != null) {
            apiVersion = methodAnnotation;
        }

        String[] urlPatterns = apiVersion == null ? new String[0] : apiVersion.value();

        PatternsRequestCondition apiPattern = new PatternsRequestCondition(urlPatterns);
        PatternsRequestCondition oldPattern = mapping.getPatternsCondition();
        PatternsRequestCondition updatedFinalPattern = apiPattern.combine(oldPattern);

        // 重新构建 RequestMappingInfo
        mapping = new RequestMappingInfo(mapping.getName(), updatedFinalPattern, mapping.getMethodsCondition(),
                mapping.getParamsCondition(), mapping.getHeadersCondition(), mapping.getConsumesCondition(),
                mapping.getProducesCondition(), mapping.getCustomCondition());

        super.registerHandlerMethod(handler, method, mapping);

    }
}
