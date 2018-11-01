package com.hacker.dubbo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Hacker
 * @date：2018/10/23
 * @project project
 * @describe
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/*.xml")
public class DubboTest {

    char c;

    @Test
    public void test3(){
        System.out.println(String.valueOf(null));
    }
}
