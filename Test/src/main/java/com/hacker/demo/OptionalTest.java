package com.hacker.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Hacker
 * @date：2018/11/1
 * @project project
 * @describe
 */
public class OptionalTest {
    
    @Test
    public void testOptional(){

        List<String> list = new ArrayList<>();

        //看下源代码就懂了
        List<String> _list = Optional.ofNullable(list).get();
        System.out.println(_list);
    }
}
