package com.gjk.designmode.zerenlian;

/**
 * ConcreteHandler2
 *
 * @author: GJK
 * @date: 2022/4/2 17:58
 * @description:
 */
public class ConcreteHandler2 extends Handler{
    @Override
    public void handleRequest(int request) {
        if(request == number){
            System.out.println("具体处理者2负责处理该请求！");
        }
        if(next != null){
            next.handleRequest(request);
        }else {
            System.out.println("请求处理完毕！");
        }

    }
}
