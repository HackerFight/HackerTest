package com.hacker.model.factory;

/**
 * @author ZhaZhaHui
 * @date：2018/9/10
 * @project project
 * @describe 工厂模式可以分为三类：
            1）简单工厂模式（Simple Factory）
            2）工厂方法模式（Factory Method）
            3）抽象工厂模式（Abstract Factory）
    工厂方法为工厂类定义了接口，用多态来削弱了工厂类的职能
     从上面创建产品对象的代码可以看出，工厂方法和简单工厂的主要区别是，简单工厂是把创建产品的职能都放在一个类里面，
     而工厂方法则把不同的产品放在实现了工厂接口的不同工厂类里面，这样就算其中一个工厂类出了问题，其他工厂类也能正常工作，
     互相不受影响，以后增加新产品，也只需要新增一个实现工厂接口工厂类，就能达到，不用修改已有的代码。
     但工厂方法也有他局限的地方，那就是当面对的产品有复杂的等级结构的时候，例如，工厂除了生产家电外产品，还生产手机产品，
     这样一来家电是手机就是两大产品家族了，这两大家族下面包含了数量众多的产品，每个产品又有多个型号，这样就形成了一个复杂的产品树了。
     如果用工厂方法来设计这个产品家族系统，就必须为每个型号的产品创建一个对应的工厂类，当有数百种甚至上千种产品的时候，
     也必须要有对应的上百成千个工厂类，这就出现了传说的类爆炸，对于以后的维护来说，简直就是一场灾难.....
 */
public class FactoryMethod {

    public static void main(String[] args) {
        Factory washerFactory = new WasherFactory();
        washerFactory.createProduct();

        Factory iceBoxFactory = new IceBoxFactory();
        iceBoxFactory.createProduct();
    }
}

//定义两个接口，一个是工厂的接口，一个是产品的接口
interface Factory{
     Product createProduct();
}

interface Product{

}

class Washer implements Product {
    public Washer() {
        System.out.println("洗衣机被创建了");
    }
}

class IceBox implements Product{
    public IceBox() {
        System.out.println("冰箱被创建了");
    }
}

//创建洗衣机的工厂
class  WasherFactory implements Factory{
    @Override
    public Product createProduct() {
        return new Washer();
    }
}

//创建冰箱的工厂
class IceBoxFactory implements Factory{

    @Override
    public Product createProduct() {
        return new IceBox();
    }
}



