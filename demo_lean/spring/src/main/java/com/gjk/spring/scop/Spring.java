package com.gjk.spring.scop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring
 *
 * @ComponentScan 包扫描配置bean
 *
 * @ImportResource xml文件配置bean
 *
 * @author: GJK
 * @date: 2022/4/22 14:29
 * @description:
 */

@Configuration
@ComponentScan("com.gjk.spring.scop")
@ImportResource("classpath:xml_test/application.xml")
public class Spring {
}
