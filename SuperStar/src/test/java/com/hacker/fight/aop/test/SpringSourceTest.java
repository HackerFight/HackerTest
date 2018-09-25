package com.hacker.fight.aop.test;

import com.hacker.fight.anno.ApplicationContextConfig;
import com.hacker.fight.anno.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Hacker
 * @date：2018/9/20
 * @project project
 * @describe
 */
public class SpringSourceTest {
    /**
     * 源码分析
     */
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        Person bean = ctx.getBean(Person.class);
        System.out.println("bean = " + bean);
    }

}
