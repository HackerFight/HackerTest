package com.hacker.fight.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author ZhaZhaHui @date：2018/9/14
 * @project project
 * @describe
 *
 *           try{ //1.前置通知
 *
 *           //目标方法执行
 *
 *           //2.后置通知(此时无法获取方法的返回值）
 *
 *           }catch(Exception e){ //3.异常通知 }
 *
 *           //4.返回通知
 */
@Component
@Aspect
public class LogAspect {

    private static final String EXP = "execution(public * com.hacker.fight.aop.Calculator.*(..))";

    @Pointcut("execution(public * com.hacker.fight.aop.Calculator.*(..))")
    public void declarePointCutExpression() {
    }

    /**
     * @param joinPoint
     */
    @Before(value = "execution(public * com.hacker.fight.aop.CalculatorImpl.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        System.out.println("前置通知 -> method name is :[" + name + "]" + Arrays.asList(joinPoint.getArgs()));
    }

    /**
     * 注意：这些个通知注解都有一个 [argNames] 属性，通过它可以在方法中加入形参，否则这些个通知[方法]是无法使用形参的，除了 JoinPoint
     * @param joinPoint
     */
    @Before(value = "execution(public * com.hacker.fight.aop.CalculatorImpl.*(..)) && args(t1,t2)")
    public void beforeAdvice(JoinPoint joinPoint, int t1, int t2) {
//        String name = joinPoint.getSignature().getName();
//        System.out.println("前置通知 -> method name is :[" + name + "]" + Arrays.asList(joinPoint.getArgs()));
        System.out.println("------------》" + (t1 + t2));

        //修改方法的参数信息
    }

    @After("declarePointCutExpression()")
    public void afterAdvice(JoinPoint joinPoint) {
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        System.out.println("后置通知 -> method name is :[" + methodName + "]");
    }

    /**
     * @param joinPoint
     * @param result  returning 中的值必需和形参保持一致
     */
    @AfterReturning(value = "declarePointCutExpression()", returning = "result")
    public void returnAdvice(JoinPoint joinPoint, Object result){
        System.out.println("返回通知 -> 方法的执行结果是: [ " + result +" ]");
    }

    /**
     * @param joinPoint
     * @param ex  throwing 中的值必需和形参保持一致
     */
    @AfterThrowing(value = "declarePointCutExpression()", throwing = "ex")
    public void exceptionAdvice(JoinPoint joinPoint, Exception ex){
        System.out.println("异常通知: " + ex);
    }

}