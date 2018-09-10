package com.hacker.test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LocalVariableTableTest {

    private static final Log LOG = LogFactory.getLog(LocalVariableTableTest.class);

    private static LocalVariableTableParameterNameDiscoverer discoverer;

    static {
        discoverer = new LocalVariableTableParameterNameDiscoverer();
    }

    public void show(String name, Integer age, String address, String[] hobbies) {
        System.out.println(name);
        //TODO......
    }

    public static void main(String[] args) {

        Map map = new HashMap();
        Object o = map.get(null);
        System.out.println("o = " + o);

        Method[] methods = LocalVariableTableTest.class.getDeclaredMethods();

        for (Method method : methods) {
            /* 获取方法名 */
            String methodName = method.getName();  //test ....
            //获取方法的参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();

            for (Class<?> parameterType : parameterTypes) {

            }

            //获取所有的参数名
            String[] parameterNames = discoverer.getParameterNames(method); /* this is a end */

            StringBuilder sb = new StringBuilder();
            for (String parameterName : parameterNames) {
                sb.append(parameterName).append("\t ");
            }
            LOG.info("======= method is [ " + methodName + " ] , and param name is :[ " + sb.toString() + "]======");
        }
    }


}
