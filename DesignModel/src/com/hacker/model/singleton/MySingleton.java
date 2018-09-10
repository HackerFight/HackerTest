package com.hacker.model.singleton;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe
 */
public class MySingleton {

    private static final MySingleton INSTANCE = new MySingleton();

    private MySingleton(){}

    public static MySingleton getInstance(){
        return INSTANCE;
    }
}
