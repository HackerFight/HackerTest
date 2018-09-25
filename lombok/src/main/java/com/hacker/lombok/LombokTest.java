package com.hacker.lombok;

import com.hacker.lombok.entry.Customer;
import com.hacker.lombok.entry.Person;
import com.hacker.lombok.entry.Student;
import lombok.extern.java.Log;

/**
 * @author ZhaZhaHui
 * @date：2018/9/17
 * @project project
 * @describe  lombok 提供了很多日志注解，具体可以查看官网   https://projectlombok.org/features/log
 *             使用了 @Log(topic = "reporting") 注解后，字节码文件中的有：  private static final Logger log = Logger.getLogger("reporting");
 *
 */
@Log(topic = "reporting")
public class LombokTest {

    public static void test1() {
        Person person = new Person();
        person.setName("傅园慧");
        System.out.println(person);
    }

    public static void test2() {
        final Student student = Student.getStudentInstance();
        student.setIdCard("150422199112095117");
        System.out.println(student);
    }

    public static void test3() {
        log.info("测试 lombok 的 日志记录");
        Customer customer = new Customer("zhangSan", "150422199112095117");
        System.out.println(customer);
    }

    public static void main(String[] args) {
//        test1();
//        test2();
        test3();

    }

}
