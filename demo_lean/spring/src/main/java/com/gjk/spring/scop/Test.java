package com.gjk.spring.scop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.gjk.spring.ioc_xml.MessageService;

/**
 * Test
 *
 * @Scope 指定bean的作用域
 * singleton 单例，每次获取的bean都指向同一个bean
 * prototype 多列，每次获取的bean都是新生成的bean
 * reqeust  同一次请求 每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
 * session  同一个会话级别  每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
 *
 * 重点关注 singleton 和 prototype
 * 当singleton bean 注入 prototype bean时，singleton 中的 prototype 指向的都是同一个，因为singleton bean只初始化一次，属性也只注入一次
 *
 * 解决办法：
 * 1.让bean A通过实现ApplicationContextAware来感知applicationContext（即可以获得容器上下文），从而能在运行时通过ApplicationContext.getBean(String beanName)的方法来获取最新的bean B。
 *
 * 2. 使用Spring的Lookup注解，请查看TestService
 *
 *
 *
 * @author: GJK
 * @date: 2022/4/22 14:25
 * @description:
 */
public class Test {

    public static void main(String[] args) {

        //注解的方式初始化容器
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Spring.class);

        //获取xml配置的bean
        MessageService messageService = applicationContext.getBean(MessageService.class);
        System.out.println(messageService.getClass().getSimpleName());


        ScopTestService testService1 = applicationContext.getBean(ScopTestService.class);
        testService1.sysout();

        ScopTestService testService2 = applicationContext.getBean(ScopTestService.class);
        testService2.sysout();

        ScopTestService testService3 = applicationContext.getBean(ScopTestService.class);
        testService3.sysout();

    }
}
