package com.hacker.model.singleton;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe
 */
public class SingletonEnum {
    public static void main(String[] args) {
        Singleton singleton = Singleton.SINGLETON;
        Singleton singleton2 = Singleton.SINGLETON;
        System.out.println(singleton == singleton2);
    }
}

enum  Singleton{
    SINGLETON;
}