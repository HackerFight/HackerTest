package com.hacker.model.factory;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe 工厂模式用于对象的创建，使得客户从具体的产品对象中被解耦。
 *   工厂模式可以分为三类：
        1）简单工厂模式（Simple Factory）
        2）工厂方法模式（Factory Method）
        3）抽象工厂模式（Abstract Factory）
 */
public class SimpleFactory {

    public static BWMCar createBWMCar(Integer type){
        switch (type){
            case 1:
                return new BWM530();
            case 2:
                return new BWM223();
            default:
              return null;
        }
    }

    public static void main(String[] args) {
        SimpleFactory.createBWMCar(1);
        SimpleFactory.createBWMCar(2);
    }
}

//接口也ok
abstract class BWMCar{

}

class BWM530 extends BWMCar{
    public BWM530(){
        System.out.println("BWM530 被创建了");
    }
}

class BWM223 extends BWMCar{
    public BWM223(){
        System.out.println("BWM223 被创建了");
    }
}
