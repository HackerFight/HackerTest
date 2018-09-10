package com.hacker.model;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe 自己实现 ArrayList
 */
public class ArrayList {
    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    private transient Object[] elementData;

    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};

    public ArrayList(){
        elementData = DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA;
    }
}
