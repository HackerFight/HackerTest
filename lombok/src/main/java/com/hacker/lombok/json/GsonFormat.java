package com.hacker.lombok.json;

import java.util.List;

/**
 * @author ZhaZhaHui
 * @date：2018/9/17
 * @project project
 * @describe
 */
public class GsonFormat {



    /**
     * students : [{"age":23,"name":"zhangsan"}]
     * teachers : [{"age":23,"name":"zhangsan"}]
     * id : wx9fdb8ble7ce3c68f
     *
     * JSON:
     *     {
             "id": "wx9fdb8ble7ce3c68f",
             "students": [
                     {
                     "age": 23,
                     "name": "zhangsan"
                     }
                        ],
           "teachers": [
                     {
                     "age": 23,
                     "name": "zhangsan"
                     }
                      ]
          }
     */

    private String id;
    private List<Student> students;
    private List<Teacher> teachers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public static class Student {
        /**
         * age : 23
         * name : zhangsan
         */

        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Teacher {
        /**
         * age : 23
         * name : zhangsan
         */

        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
