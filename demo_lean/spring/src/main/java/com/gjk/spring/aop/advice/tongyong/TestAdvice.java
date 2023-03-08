package com.gjk.spring.aop.advice.tongyong;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * TestAdvice
 *
 * aop切面实现
 *
 * AOP实现是基于JDK动态代理和Cglib动态代理的
 *
 *
 * Spring5.2.6版本及之前版本执行顺序
 * 正常执行：around_before -> before -> 执行方法  -> after -> afterReturning
 * 发生异常：around_before -> before -> 执行方法 -> around_after -> after -> afterThrowing
 *
 * Spring5.2.7版本及之后版本执行顺序
 * 正常执行：around_before -> before -> 执行方法 -> afterReturning -> after -> around_after
 * 发生异常：around_before -> before -> 执行方法 -> afterThrowing -> after
 *
 *
 * @author: GJK
 * @date: 2022/3/31 12:33
 * @description:
 */
@Aspect
@Component
public class TestAdvice {


    /**
     * 可在已有老功能中添加新功能，及新功能对象包含了新老功能的方法和属性
     */
    @DeclareParents(value = "com.gjk.spring.aop.advice.tongyong.*",defaultImpl = TestService.class)
    private static IService service;


    @Pointcut("execution(* com.gjk.spring.aop.advice.tongyong.TestService.eatCarrot())")
    private void eatCarrot(){}

    @Before("eatCarrot()")
    public void handlerRpcResultBefore(JoinPoint point) {
        String classname = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        List<Object> args = Arrays.asList(point.getArgs());
        System.out.println("@before Execute! --class name: " + classname + ", method name: " + methodName + " " + args );
    }


    @Around("eatCarrot()")
    public void handlerRpcResultAround(ProceedingJoinPoint point) throws Throwable {
        System.out.println("@Around：执行目标方法之前...");
        Object proceed = point.proceed();
        System.out.println("@Around：执行目标方法之后...");
        System.out.println("@Around：被织入的目标对象为：" + point.getTarget());
        System.out.println( "@Around：原返回值：" + proceed + "，这是返回结果的后缀");
    }

    @After("eatCarrot()")
    public void handlerRpcResultAfter(JoinPoint point) {
        System.out.println("@After：模拟释放资源...");
        System.out.println("@After：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@After：参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@After：被织入的目标对象为：" + point.getTarget());
    }



    @AfterReturning("eatCarrot()")
    public void handlerRpcResultAfterReturning(JoinPoint point) {
        System.out.println("@AfterReturning：模拟日志记录功能...");
        System.out.println("@AfterReturning：目标方法为：" +
                point.getSignature().getDeclaringTypeName() +
                "." + point.getSignature().getName());
        System.out.println("@AfterReturning：参数为：" +
                Arrays.toString(point.getArgs()));
        System.out.println("@AfterReturning：返回值为：" );
        System.out.println("@AfterReturning：被织入的目标对象为：" + point.getTarget());
    }


    @AfterThrowing("eatCarrot()")
    public void handlerRpcResultAfterThrowing() {
        System.out.println("异常通知....");
    }

//    @Around("execution(* com.gjk.demo_lean.spring.advice.tongyong.TestService.eatCarrot())")
//    public void handlerRpcResult2(ProceedingJoinPoint point) throws Throwable {
//        System.out.println("吃萝卜前洗手2");
//        point.proceed();
//        System.out.println("吃萝卜后买单2");
//    }

}
