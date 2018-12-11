package com.hacker.jedis.test;

import com.hacker.jedis.entrty.Person;
import com.hacker.jedis.utils.MapUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hacker
 * @date：2018/12/7
 * @project project
 * @describe
 */
public class MainTest {

    @Test
    public void test1() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("name", "lisi");
        map.put("age", 24);
        map.put("phone", "10086");
        Person person = new Person();
        MapUtils.conversionBean(map, person);
        System.out.println(person);
    }
}
