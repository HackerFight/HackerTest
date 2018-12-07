package com.hacker.jedis.test;

import java.lang.reflect.Field;

/**
 * @author Hacker
 * @date：2018/12/7
 * @project project
 * @describe
 */
public class ExtendsTest {

    public static void main(String[] args) throws  Exception {

        Class clazz = Audi.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            System.out.println(declaredFields[i].getName());
        }

        System.out.println("----------------------------------------");

        //循环向上遍历 找到父类及父类的父类所有的属性
        //Object 类中的方法就不需要了
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            Object o = clazz.newInstance();
            for (Field field : fields) {
                //field.setAccessible(true);
                System.out.println(field.getName());
                //如果想要进行对属性进行设置值等操作，那么必需  field.setAccessible(true);
                //如果只是访问，查看属性名，那么就不需要了
                if ("brand".equals(field.getName())) {
                    field.set(o, "BWM");
                }
            }
        }
    }
}


class Car {
    private String name;

    private String balance;

    private String brand;
}

class Audi extends Car{

}