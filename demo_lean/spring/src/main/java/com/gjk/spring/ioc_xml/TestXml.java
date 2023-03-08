package com.gjk.spring.ioc_xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TestXml
 *
 * @author: GJK
 * @date: 2022/4/20 17:17
 * @description:
 */
public class TestXml {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:xml_test/application.xml");
        System.out.println("context 启动成功");
        MessageService messageService = applicationContext.getBean(MessageService.class);
        String message = messageService.getMessage();
        System.out.println(message);
    }
}
