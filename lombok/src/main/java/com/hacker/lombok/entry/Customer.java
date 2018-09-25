package com.hacker.lombok.entry;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZhaZhaHui
 * @date：2018/9/17
 * @project project
 * @describe @RequiredArgsConstructor 这个注解会将标注了 @NonNull 或者 final 修饰的属性作为 构造器的参数，其他没有标注的将不会生成
 *            @Setter  标注在类上，将会为类中所有的属性生成 set方法
 *            @ToString 生成 toString() 方法
 */
@ToString
@Setter
@Getter
@RequiredArgsConstructor
public class Customer {

    @NonNull
    private String name;

    private String address;

    @NonNull
    private String idCard;

    private String cellphone;

}
