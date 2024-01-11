package com.gjk.spring.configuartion.proxyBeanMethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gjk.spring.configuartion.proxyBeanMethod.bean.Teacher;
import com.gjk.spring.configuartion.proxyBeanMethod.config.ProxyBeanMethodTrue;

/**
 * ProxyBeanMethodApplication
 *
 * @author: GJK
 * @date: 2024/1/11 12:04
 * @description:
 */
@SpringBootApplication
public class ProxyBeanMethodApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProxyBeanMethodApplication.class, args);

        ProxyBeanMethodTrue config = context.getBean(ProxyBeanMethodTrue.class);
        Teacher teacher1 = config.teacher();
        Teacher teacher2 = config.teacher();
        boolean flag = teacher1 == teacher2;
        System.out.println("当ProxyBeanMethod为" + context.getEnvironment().getProperty("proxy.bean.method.enable") + "时，两次从容器中获取的@bean对象是否相同:" + flag);


    }
}
