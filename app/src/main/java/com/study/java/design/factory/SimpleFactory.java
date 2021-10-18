package com.study.java.design.factory;

/**
 * 简单工厂模式
 * 该模式对对象创建管理方式最为简单，因为其仅仅简单的对不同类对象的创建进行了一层薄薄的封装。
 * 该模式通过向工厂传递类型来指定要创建的对象
 */
public class SimpleFactory {

    public static void main(String[] args) {
        PhoneFactory factory = new PhoneFactory();
        Phone miPhone = factory.makePhone("MiPhone");
        IPhone iPhone = (IPhone) factory.makePhone("iPhone");
    }
}
