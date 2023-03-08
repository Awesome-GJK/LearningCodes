package com.gjk.spring.aop.advice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GlobalErrorCatch
 * 自定义注解
 *
 * @author: GJK
 * @date: 2022/3/31 15:11
 * @description:
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalErrorCatch {

    /**
     * 使用注解时，可以添加方法名
     * @return
     */
    String methodName() default "";
}
