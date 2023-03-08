package com.gjk.spring.aop.advice.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * GlobalErrorCatchAdvice
 * 自定义注解+AOP切面
 *
 * @author: GJK
 * @date: 2022/3/31 15:34
 * @description:
 */
@Aspect
@Component
public class GlobalErrorCatchAdvice {

    /**
     * 将注解作为切面
     */
    @Pointcut("@annotation(com.gjk.spring.aop.advice.annotation.GlobalErrorCatch)")
    private void globalCatch(){}




    @Around("globalCatch()")
    public void aroundGlobalError(ProceedingJoinPoint point) throws Throwable {
        System.out.println("around前置环绕");
        try {
            String proceed = point.proceed().toString();
        }catch (Exception e){
            System.out.println("方法"+ point.getTarget().getClass().getSimpleName());
        }finally {
            System.out.println("around后置环绕");
        }
    }
}
