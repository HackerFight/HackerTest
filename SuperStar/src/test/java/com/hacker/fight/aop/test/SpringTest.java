package com.hacker.fight.aop.test;

import com.hacker.fight.anno.ApplicationContextConfig;
import com.hacker.fight.aop.Calculator;
import com.hacker.fight.aop.GlobalContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ZhaZhaHui
 * @date：2018/9/14
 * @project project
 * @describe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GlobalContextConfig.class, ApplicationContextConfig.class})
public class SpringTest {

    @Autowired
    ApplicationContext applicationContext;
    
    @Test
    public void testAop(){
        Calculator calculator = applicationContext.getBean(Calculator.class);
        System.out.println(calculator.getClass());
//        int sum = calculator.sum(45.0, 35.0);
//        System.out.println("sum = " + sum);

        System.out.println("--------------------------------------------");

        int div = calculator.div(100, 2);
        System.out.println(div);
    }
}
