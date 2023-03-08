package com.gjk.spring.aop.advice.tongyong;

import org.springframework.stereotype.Service;

/**
 * TestService
 *
 * @author: GJK
 * @date: 2022/3/31 11:37
 * @description:
 */
@Service
public class TestService implements IService{

    @Override
    public String eatCarrot() {
        System.out.println("吃萝卜");
        return "吃萝卜result";
    }

    @Override
    public String eatMushroom() {
        System.out.println("吃蘑菇");
        return "吃蘑菇result";
    }

    @Override
    public String eatCabbage() {
        System.out.println("吃白菜");
        return "吃白菜result";
    }
}
