package com.gjk.spring.aop.advice.annotation;

import org.springframework.stereotype.Service;

/**
 * personService
 *
 * @author: GJK
 * @date: 2022/3/31 15:31
 * @description:
 */
@Service
public class PersonService {


    @GlobalErrorCatch(methodName = "eat")
    public String eat(){
        System.out.println("=====eat=====");
        return "eat";
    }

}
