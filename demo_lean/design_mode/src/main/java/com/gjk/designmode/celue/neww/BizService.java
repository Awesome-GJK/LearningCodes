package com.gjk.designmode.celue.neww;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * BizService
 * 某个业务服务类
 *
 *  基于lambda表达式的策略模式
 *
 * @author: GJK
 * @date: 2022/3/29 15:04
 * @description:
 */
@Service
public class BizService {
    @Autowired
    private BizUnitService bizUnitService;

    /**
     * 策略实现容器
     */
    private Map<String, Function<String, String>> checkResultDispatcherComX = new HashMap<>();

    @PostConstruct
    public void checkResultDispatcherComXInit(){
        checkResultDispatcherComX.put("key_订单1", order -> bizUnitService.bizOne(order));
        checkResultDispatcherComX.put("key_订单2", order -> bizUnitService.bizTwo(order));
        checkResultDispatcherComX.put("key_订单3", order -> bizUnitService.bizThree(order));
    }

    public String getCheckResultComX(String order, int level){
        //生成key的逻辑
        String ley = getDispatcherComXKey(order, level);
        Function<String, String> function = checkResultDispatcherComX.get(ley);
        if (function != null) {
            //执行这段表达式获得String类型的结果
            return function.apply(order);
        }
        return "不在处理的逻辑中返回业务错误";
    }


    /**
     * 判断条件方法
     */
    private String getDispatcherComXKey(String order, int level) {
        StringBuilder key = new StringBuilder("key_");
        key.append(order).append(level);
        return key.toString();
    }

}
