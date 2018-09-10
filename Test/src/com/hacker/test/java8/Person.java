package com.hacker.test.java8;

/**
 * @author ZhaZhaHui
 * @version 1.0.0
 * @date：2018/7/11
 * @describe
 */
public class Person implements  Comparable<Object> {

    private String name;
    private String idCard; /*  身份证  */
    private int age;

    @Override
    public int compareTo(Object o) {
        if (o instanceof  Person) {
            Person person = (Person) o;
             //按照年龄排序
            int  result;
            result = getAge() - person.getAge();
             //如果年龄一样再去比较姓名的首字母
            if (0 == result) {
               // result = ((int) getName().charAt(0) - (int) person.getName().charAt(0));
                result = getName().compareTo(person.getName());
            }
            return result;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return idCard != null ? idCard.equals(person.idCard) : person.idCard == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (idCard != null ? idCard.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
