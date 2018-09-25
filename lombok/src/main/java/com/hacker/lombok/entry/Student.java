package com.hacker.lombok.entry;

import lombok.Data;
import lombok.ToString;

/**
 * @author ZhaZhaHui
 * @date：2018/9/17
 * @project project
 * @describe: 通过 staticConstructor 属性，可以 将构造器私有化，并生成一个静态方法返回对象
        private Student() {}

        public static Student studentFactory() {
        return new Student();
        }

       @Data 注解本身会给我们生成 所有参数的 toString() 方法，我们也可以通过 @ToString 注解的 属性来剔除不需要输出的 属性
 */
@Data(staticConstructor = "getStudentInstance")
@ToString(exclude = "name")
public class Student {

    private String name;

    private String address;

    private String idCard;
}
