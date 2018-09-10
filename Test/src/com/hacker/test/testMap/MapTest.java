package com.hacker.test.testMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZhaZhaHui
 * @version 1.0.0
 * @date：2018/8/31
 * @describe
 */
public class MapTest {

    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee("1001", "HBX", "23", "1");
        Employee employee1 = new Employee("1002", "WS", "24", "0");
        Employee employee2 = new Employee("1003", "SJ", "25", "0");
        Employee employee3 = new Employee("1004", "FYH", "26", "1");
        employees.add(employee);
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        Map<String, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getSex));
        System.out.println(collect);


    }
}

class  Employee{

    private String id;

    private String name;

    private String age;

    private String sex;

    public String getId() {
        return id;
    }

    public Employee() {

    }

    public Employee(String id, String name, String age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}