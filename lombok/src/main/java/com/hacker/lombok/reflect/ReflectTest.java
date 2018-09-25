package com.hacker.lombok.reflect;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.java.Log;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ZhaZhaHui
 * @date：2018/9/18
 * @project project
 * @describe
 */
@Log
public class ReflectTest {

    private  LocalVariableTableParameterNameDiscoverer discoverer;

    {
        discoverer = new LocalVariableTableParameterNameDiscoverer();
    }

    public <T> void reflect(Class<T> clazz) {
        String classPath = clazz.getCanonicalName();  //类路径
        log.info("------classpath: " + classPath + " ---------------");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        log.info("------declaredMethods: " + declaredMethods.length + " ---------------");
        if (!ObjectUtils.isEmpty(declaredMethods)) {
            for (Method declaredMethod : declaredMethods) {
                if (!declaredMethod.getDeclaringClass().equals(clazz)) {
                    continue;  //继承的方法不注册
                }
                if (Modifier.isStatic(declaredMethod.getModifiers())) {
                    continue;  //静态方法不注册
                }
                registerMethod(declaredMethod, clazz);
            }
        }
    }

    //注册
    private void registerMethod(Method method, Class clazz){
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = discoverer.getParameterNames(method);
        JSONArray jsonArray = new JSONArray();
        log.info("parameterTypes [" + Arrays.asList(parameterTypes)+ "]");
        int index = 0 ;
        for (Class<?> parameterType : parameterTypes) {
           Map<String, Object> paramsMap = new TreeMap<>();
           paramsMap.put("name", parameterNames[index]);
           paramsMap.put("type", parameterType.getSimpleName());
           paramsMap.put("order", index++);
           jsonArray.add(paramsMap);
        }

        System.out.println("json: " + jsonArray);

    }

    public static <T>  void test(Class<T> clazz){
        try {
            Method sayHello = clazz.getDeclaredMethod("sayHello", String.class, Integer.class, Date.class, Double.class);
            sayHello.setAccessible(true);
            Object invoke = sayHello.invoke(clazz.newInstance(), "", 0, null, null);
            System.out.println(invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test2(){
        Class<Person> clazz = Person.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            declaredMethod.setAccessible(true);
            int parameterCount = declaredMethod.getParameterCount();
            Object[] params = new Object[parameterCount];
            try {
                declaredMethod.invoke(clazz.newInstance(), params);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
       ReflectTest test = new ReflectTest();
//       test.reflect(Person.class);
        test(Person.class);
        //test2();
    }

}
