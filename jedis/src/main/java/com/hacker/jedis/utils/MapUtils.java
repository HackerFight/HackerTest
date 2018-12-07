package com.hacker.jedis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hacker
 * @date：2018/12/3
 * @project project
 * @describe
 */
public class MapUtils {

    //导入的是 slf4j
    private static final Logger logger = LoggerFactory.getLogger(MapUtils.class);

    //将javaBean 转成 Map ，利用反射

    public static Map<String, String> conversionMap(Object object) {
        Map<String, String> map = new HashMap<>();
        try {
            if (null == object) {
                return map;
            }
            //注意:参数要传Object，不然传 Class 的话，无法得到属性值
            Class<?> clazz = object.getClass();
            /**
             * 得到所有的属性（包括继承来的，但是Object 就不考虑了)
             * 注意：不能直接就  clazz.getDeclaredFields()   因为他只可以获取本类中的所有属性
             * 而且：getFields()：既能获取本类的属性也能得到父类的属性，但仅仅能获取public修饰的字段。
             */
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    //key 是属性名， value 是 属性值
                    //这个 get(object) 方法中的 object， 并不是 都要  clazz.newInstance(); 在设置值的时候可会需要
                    map.put(field.getName(), field.get(object) + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("{} 反射出错", object);
            logger.error("", e);
        }
        return map;
    }
}
