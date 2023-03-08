package com.gjk.spring.interceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * InterceptController
 *
 * @author: GJK
 * @date: 2022/7/11 17:59
 * @description:
 */

@RestController
public class InterceptController {


    @GetMapping("/test/login")
    public String testInterceptByLogin(){
        System.out.println("/test/login");
        return "login";
    }


    @GetMapping("/test/other")
    public String testInterceptByOther(){
        System.out.println("/test/other");
        return "other";
    }
}
