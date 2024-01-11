package com.gjk.spring.configuartion.proxyBeanMethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gjk.spring.configuartion.proxyBeanMethod.config.ProxyBeanMethodFalse;

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

        ProxyBeanMethodFalse config1 = context.getBean(ProxyBeanMethodFalse.class);
        ProxyBeanMethodFalse config2 = context.getBean(ProxyBeanMethodFalse.class);
        String ProxyBeanMethodEnable = context.getEnvironment().getProperty("proxy.bean.method.enable");
        boolean flag = config1 == config2;
        System.out.println("当ProxyBeanMethod为" + ProxyBeanMethodEnable + "时，两次获取的同一个bean是否相同:" + flag);

    }
}
