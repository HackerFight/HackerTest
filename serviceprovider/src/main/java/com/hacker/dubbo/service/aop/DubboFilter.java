package com.hacker.dubbo.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Hacker
 * @date：2018/10/22
 * @project project
 * @describe
 */
@Component
@Aspect
public class DubboFilter {

    ApplicationContext applicationContext;

    public DubboFilter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("applicationContext = " + applicationContext);
    }

    @Pointcut("execution(* com.hacker.dubbo.service.impl.*.*(..))")
    public void pointCut(){}


    @Before("pointCut()")
    public void certification(JoinPoint joinPoint){
        String name = joinPoint.getSignature().getName();
        System.out.println("method name is " + name);
    }

}
