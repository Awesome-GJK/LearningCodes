package com.gjk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.gjk.spring.aop.advice.tongyong.IService;
import com.gjk.spring.aop.advice.tongyong.TestService;
import com.gjk.spring.aop.advice.tongyong.TestService1;

/**
 * SprApplication
 *
 * @author: GJK
 * @date: 2022/4/26 13:18
 * @description:
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class SprApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SprApplication.class, args);

        //测试aop
        TestService testService = context.getBean(TestService.class);
        testService.eatCarrot();

        //测试aop-@DeclareParents
        TestService1 testService1 = context.getBean(TestService1.class);
        IService service = (IService) testService1;
        service.eatCarrot();
        testService1.brushTeeth();
    }
}
