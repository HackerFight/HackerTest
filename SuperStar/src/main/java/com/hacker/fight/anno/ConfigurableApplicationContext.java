package com.hacker.fight.anno;

/**
 * @author ZhaZhaHui
 * @date：2018/9/19
 * @project project
 * @describe
 */
public class ConfigurableApplicationContext implements  ApplicationContext{

    @Override
    public <T> T getBean(String name) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> void register(Class<T> clazz) {

    }
}
