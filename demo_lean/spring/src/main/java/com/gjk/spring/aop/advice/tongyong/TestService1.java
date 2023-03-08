package com.gjk.spring.aop.advice.tongyong;

import org.springframework.stereotype.Service;

/**
 * TestService1    扩展功能，或者新功能
 *
 * @author: GJK
 * @date: 2022/4/25 17:05
 * @description:
 */
@Service
public class TestService1 {

    public void brushTeeth(){
        System.out.println("------------刷牙-------------");
    }
}
