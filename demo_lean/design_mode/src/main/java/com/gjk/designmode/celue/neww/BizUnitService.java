package com.gjk.designmode.celue.neww;

import org.springframework.stereotype.Service;

/**
 * BizUnitService
 * 提供业务逻辑单元
 *
 * 各个策略的具体实现，放一起提高可读性。
 *
 * @author: GJK
 * @date: 2022/3/29 15:04
 * @description:
 */
@Service
public class BizUnitService {

    public String bizOne(String order) {
        return order + "各种花式操作1";
    }
    public String bizTwo(String order) {
        return order + "各种花式操作2";
    }
    public String bizThree(String order) {
        return order + "各种花式操作3";
    }



}
