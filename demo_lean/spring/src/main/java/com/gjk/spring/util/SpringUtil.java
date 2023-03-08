package com.gjk.spring.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringUtil
 *
 * @author: Thunisoft
 * @date: 2021/9/27 16:59
 * @description:
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private static void initApplicationContext(ApplicationContext applicationContext){
        SpringUtil.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        initApplicationContext(applicationContext);
    }


    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }



}
