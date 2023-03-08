package com.gjk.designmode.celue.old;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Context
 * 不适用lambda表达式的策略模式
 *
 *
 * @author: GJK
 * @date: 2022/3/29 14:52
 * @description:
 */
public class Context {



    //第一种写法 第一种是维护了一个strategies的Map容器。用这种方式就需要判断每种策略是否可以共享使用，它只是作为算法的实现。
    /**
     * 缓存所有的策略，当前是无状态的，可以共享策略类对象
     */
    private static final Map<String, GearStrategy> strategies = new HashMap<>();

    static {
        strategies.put("one", new GearStrategyOne());
        strategies.put("two", new GearStrategyTwo());
    }

    public static GearStrategy getStrategy(String type) {
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        return strategies.get(type);
    }


    //第二种写法 第二种是直接通过有状态的类，每次根据类型new一个新的策略类对象。这个就需要根据实际业务场景去做的判断。
    public static GearStrategy getStrategySecond(String type) {
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("type should not be empty.");
        }
        if (type.equals("one")) {
            return new GearStrategyOne();
        }else if(type.equals("two")){
            return new GearStrategyTwo();
        }
        return null;
    }


    public static void main(String[] args) {
        //测试结果
        GearStrategy one = Context.getStrategy("one");
        one.algorithm("1档");
        // 结果：当前档位1档
        GearStrategy strategyTwo = Context.getStrategySecond("one");
        strategyTwo.algorithm("1档");
        // 结果：当前档位1档
    }

}
