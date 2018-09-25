package com.hacker.dubbo.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Hacker
 * @date：2018/9/22
 * @project project
 * @describe
 */
public class MainApplication {

    public static void main(String[] args) throws Exception{

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-provider.xml");
        ctx.start();
        System.out.println("service provider start......");
        System.in.read();
    }
}
