package com.hacker.model.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ZhaZhaHui @date：2018/9/10
 * @project project
 * @describe
 */
public class DynamicProxy {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        SubjectDynamicProxy proxy = new SubjectDynamicProxy(realSubject);
        Subject proxyObject = proxy.getProxyObject(); //代理对象
        proxyObject.say();
    }
}

interface Subject {
    void say();
}

// 被代理类（客户）
class RealSubject implements Subject {

    @Override
    public void say() {
        System.out.println("动态代理.....");
    }
}

class SubjectDynamicProxy implements InvocationHandler {
    //同样让其持有接口的引用，用Object也ok
    private Subject object;

    public SubjectDynamicProxy(Subject object) {
        this.object = object;
    }

    /**
     * 调用被代理的方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("目标方法即将被执行..........");
        //目标方法的返回值
        Object invoke = method.invoke(object, args);
        System.out.println("目标方法执行完毕");
        return invoke;
    }

    /**
     * 获取代理对象
     * @return
     */
    public Subject getProxyObject(){
       return  (Subject)Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }
}
