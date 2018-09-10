package com.hacker.model.singleton;

/**
 * @author ZhaZhaHui @date：2018/9/10
 * @project project
 * @describe
 */
public class MySingletonPattern {

    private static MySingletonPattern INSTANCE = null;

    private MySingletonPattern() {
    }

    public static MySingletonPattern getInstance() {
        synchronized (MySingletonPattern.class) {
            if (null == INSTANCE) {
                INSTANCE = new MySingletonPattern();
            }
        }
        return INSTANCE;
    }
}
