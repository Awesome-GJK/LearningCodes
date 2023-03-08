package com.gjk.spring.interceptor;

import javax.xml.transform.Source;

import org.springframework.stereotype.Service;

/**
 * InterceptorService
 *
 * @author: GJK
 * @date: 2022/7/12 11:25
 * @description:
 */
@Service
public class InterceptorService {

    public void a(){
        System.out.println("InterceptorService#a()执行.....");
    }
}
