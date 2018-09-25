package com.hacker.fight.aop;

import org.springframework.stereotype.Component;

/**
 * @author ZhaZhaHui
 * @date：2018/9/14
 * @project project
 * @describe
 */
@Component
public class CalculatorImpl implements Calculator {

    @Override
    public int sum(int t1, int t2) {
        System.out.println("目标方法开始执行");
        return Integer.sum(t1, t2);
    }

    @Override
    public int div(int t1, int t2) {
        System.out.println("目标方法开始执行");
        return t1/t2;
    }
}
