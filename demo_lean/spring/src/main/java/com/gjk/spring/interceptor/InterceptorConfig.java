package com.gjk.spring.interceptor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * InterceptorConfig
 *
 * @author: GJK
 * @date: 2021/12/21 14:52
 * @description:
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Resource
    private FristInterceptor fristInterceptor;

    @Resource
    private SecondInterceptor secondIntercept;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fristInterceptor).addPathPatterns("/test/**").excludePathPatterns("/test/login");
        registry.addInterceptor(secondIntercept).addPathPatterns("/test/**").excludePathPatterns("/test/login");
    }
}
