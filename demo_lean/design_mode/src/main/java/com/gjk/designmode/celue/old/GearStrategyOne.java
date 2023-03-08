package com.gjk.designmode.celue.old;

/**
 * GearStrategyOne
 *
 * @author: GJK
 * @date: 2022/3/29 14:51
 * @description:
 */
public class GearStrategyOne implements GearStrategy {
    @Override
    public void algorithm(String param) {
        System.out.println("当前档位" + param);
    }
}
