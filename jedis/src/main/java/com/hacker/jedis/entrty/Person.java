package com.hacker.jedis.entrty;

/**
 * @author Hacker
 * @date：2018/11/30
 * @project project
 * @describe
 */
public class Person {

    private String name;

    private Integer age;

    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Person(String name, Integer age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public Person() {
    }
}
