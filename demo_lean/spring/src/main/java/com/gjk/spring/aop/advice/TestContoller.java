package com.gjk.spring.aop.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.gjk.spring.aop.advice.annotation.PersonService;
import com.gjk.spring.aop.advice.tongyong.TestService;

import lombok.RequiredArgsConstructor;

/**
 * TestContoller
 *
 * @author: GJK
 * @date: 2022/3/31 13:12
 * @description:
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestContoller {

    private final TestService testService;

    private final PersonService personService;

    @GetMapping("eat")
    public void eat(){
        testService.eatCabbage();
        testService.eatCarrot();
        testService.eatMushroom();
    }

    @GetMapping("chi")
    public void chi(){
        personService.eat();
    }
}
