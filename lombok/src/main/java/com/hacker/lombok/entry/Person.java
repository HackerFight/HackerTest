package com.hacker.lombok.entry;

import lombok.*;

/**
 * @author ZhaZhaHui
 * @date：2018/9/17
 * @project project
 * @describe @Data 注解：通过字节码文件可以看到，他会提供 getter、setter、equals、canEqual、hashCode、toString方法。
 */
@Data
public class Person {

    private String name;

    private String address;

    private String idCard;

    private String cellphone;

    private double salary;

    private int age;

    private boolean isExists;

    public Person() {
    }

    public boolean isExists() {
        return this.isExists;
    }

    public void setExists(boolean isExists) {
        this.isExists = isExists;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Person;
    }

}
