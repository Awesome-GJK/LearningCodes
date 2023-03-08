package com.gjk.designmode.zerenlian;

/**
 * ConcreteHandler1
 *
 * @author: GJK
 * @date: 2022/4/2 17:52
 * @description:
 */
public class ConcreteHandler1 extends Handler{
    @Override
    public void handleRequest(int request) {
        //满足条件，处理具体业务
        if(request == number){
            System.out.println("具体处理者1负责处理该请求！");
            number++;
        }
        //如果不等于空，处理下一环境任务
        if(next != null){
            next.handleRequest(request);
        }else{
            System.out.println("请求处理完毕！");
        }

    }

}
