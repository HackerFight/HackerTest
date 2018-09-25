package com.hacker.lombok.reflect;

import lombok.Data;

import java.util.Date;

/**
 * @author ZhaZhaHui
 * @date：2018/9/18
 * @project project
 * @describe
 */
@Data
public class Person implements Human {

    private String name;
    
    private String idCard;
    
    private Date birthday;

    private int age;

    private String address;

    private Double salary;

    public static String showInfo(String name){
        return "Hello World";
    }

    private void sayHello(String name, Integer age, Date birthday, Double salary){
        System.out.println("Hello Miss");
    }

    @Override
    public void shutdown() {
        System.out.println("关机");
    }
}
