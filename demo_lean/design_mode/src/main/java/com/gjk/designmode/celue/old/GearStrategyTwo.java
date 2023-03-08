package com.gjk.designmode.celue.old;

/**
 * GearStrategyTwo
 *
 * @author: GJK
 * @date: 2022/3/29 14:59
 * @description:
 */
public class GearStrategyTwo implements GearStrategy {
    @Override
    public void algorithm(String param) {
        System.out.println("当前档位" + param);
    }
}
