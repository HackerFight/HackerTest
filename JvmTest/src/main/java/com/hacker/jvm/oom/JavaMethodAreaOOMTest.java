package com.hacker.jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Hacker
 * @date：2018/11/13
 * @project project
 * @describe 借助cglib 使方法区出现内存溢出(引入cglib的依赖)
 *
 *   VM Args: -XX:PermSize=10M  -XX:MaxPermSize=10M'
 *   由于我用的是jdk8,关于永久代的参数已经失效了，所以需要使用元空间来代替
 *            -XX:MetaspaceSize=5M -XX:MaxMetaspaceSize=5M
 */
public class JavaMethodAreaOOMTest {

    //创建一个内部类
    static class OOMObject {

    }

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, args);
                }
            });

            enhancer.create();
        }
    }
}
