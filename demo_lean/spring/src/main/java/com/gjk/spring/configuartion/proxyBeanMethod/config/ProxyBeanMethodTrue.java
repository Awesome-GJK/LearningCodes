package com.gjk.spring.configuartion.proxyBeanMethod.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gjk.spring.configuartion.proxyBeanMethod.bean.Student;

/**
 * ProxyBeanMethodTrue
 *
 * @author: GJK
 * @date: 2024/1/11 11:57
 * @description:
 */
@Configuration(proxyBeanMethods = true)
@ConditionalOnProperty(prefix = "proxy.bean.method", name = "enable", havingValue = "true")
public class ProxyBeanMethodTrue {

    @Bean
    public Student student1(){
        Student student1 = new Student("刘涛");
        return student1;
    }

    @Bean
    public Student student2(){
        Student student2 = new Student("李磊");
        return student2;
    }
}
