package com.hacker.fight.anno;

/**
 * @author ZhaZhaHui
 * @date：2018/9/19
 * @project project
 * @describe
 */
public interface ApplicationContext {

    <T> T getBean(String name);

    <T> T getBean(Class<T> clazz);

    <T> void register(Class<T> clazz);
}
