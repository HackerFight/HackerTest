package com.hacker.fight.anno;

/**
 * @author ZhaZhaHui
 * @date：2018/9/19
 * @project project
 * @describe
 */
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
