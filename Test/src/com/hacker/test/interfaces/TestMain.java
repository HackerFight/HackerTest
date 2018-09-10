package com.hacker.test.interfaces;


public class TestMain {


    public void plan(Class clazz){
        String name = clazz.getInterfaces()[0].getName();
        System.out.println(name);


    }

    public static void main(String[] args) {
        TestMain tm = new TestMain();
        tm.plan(HelloServiceImpl.class);
    }
}
