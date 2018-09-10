package com.hacker.model.proxy;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe 所谓静态代理即在程序运行前代理类就已经存在，也就是说我们编写代码的时候就已经把代理类的代码写好了，而动态代理则是在程序运行时自动生成代理类。
 *   静态代理角色：
 *      1.目标接口
 *      2.代理类 （内部含有目标接口的引用,负责对真实角色的调用，并在真实主题角色处理前后做预处理和善后工作。)
 *      3.被代理类 (代理类和被代理类都要实现目标接口)
 */
public class StaticProxy {
    public static void main(String[] args) {
        House customer = new Customer();
        IntermediaryProxy intermediary = new IntermediaryProxy(customer);
        intermediary.buyHouse();
    }
}

//比如买房
interface House{
    void buyHouse();
}

//被代理类（客户）
class Customer implements House{
    @Override
    public void buyHouse() {
        System.out.println("客户想要买房");
    }
}

//代理类（中介）
 class IntermediaryProxy implements House{
    House house;

    public IntermediaryProxy(House house){
        this.house = house;
    }
    @Override
    public void buyHouse() {
        System.out.println("帮客户降低一下成本");
        house.buyHouse();
        System.out.println("帮客户购房成功");
    }
}

