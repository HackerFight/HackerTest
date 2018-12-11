package com.hacker.jedis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    /**
     * 将javaBean 转成 Map ，利用反射
     * @param object
     * @return
     */
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

    /**
     * 为什么 Person 中的 age 是 Integer 就报类型错误了呢?
     *   最后发现：问题不是出在这里，而是在 map 中，map.put("age", "24");
     *   这样在    Object value = source.get(name);  拿到的 value 是 String 类型
     *   而在：  writeMethod.invoke(target, value); 这里本应该是 Integer 类型，所以报错了
     *   解决办法：在map中 将字符串的 24 改为  Integer 类型的 24
     * @param source
     * @param target
     */
    public static void conversionBean(Map<String, Object> source, Object target){
        if (CollectionUtils.isEmpty(source)) {
            return;
        }
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                //属性名
                String name = propertyDescriptor.getName();
                if (source.containsKey(name)) {
                    Object value = source.get(name);
                    //得到属性对应的setter 方法
                    Method writeMethod = propertyDescriptor.getWriteMethod();// setter  /  getReadMethod() -> get()
                    writeMethod.invoke(target, value);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
