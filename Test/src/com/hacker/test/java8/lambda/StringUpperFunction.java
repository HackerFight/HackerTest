package com.hacker.test.java8.lambda;

/**
 * @author ZhaZhaHui
 * @version 1.0.0
 * @date：2018/7/18
 * @describe
 */

@FunctionalInterface
public interface StringUpperFunction<T> {
    T getValue(T contexts);
}
