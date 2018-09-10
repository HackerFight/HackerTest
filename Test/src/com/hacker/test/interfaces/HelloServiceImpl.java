package com.hacker.test.interfaces;

public class HelloServiceImpl implements  IHelloService{

    @Override
    public String hello(String words) {
        return "你好: " + words;
    }
}
