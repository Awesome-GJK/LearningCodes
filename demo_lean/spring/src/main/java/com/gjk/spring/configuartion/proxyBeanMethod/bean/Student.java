package com.gjk.spring.configuartion.proxyBeanMethod.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Student
 *
 * @author: GJK
 * @date: 2024/1/11 12:00
 * @description:
 */
@Data
@AllArgsConstructor
public class Student {

    private String name;

    public void speekName(){
        System.out.println("my name is " + name);
    }
}
