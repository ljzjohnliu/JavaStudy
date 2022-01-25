package com.study.java.design.staticproxy;

public abstract class Wanda extends Cinema {
    public Wanda() {
        super(null);

    }

    @Override
    public void play() {
        System.out.println("----------play----------");
    }

    /**
     * 如果实现了接口的类，不能实现接口中全部方法，那么需要声明成抽象类
     * 抽象类实例化的时候需要实现抽象方法。
     */
    public static void main(String[] args) {
        Wanda wanda = new Wanda() {
            @Override
            public void play() {
                super.play();
            }
        };
//        Wanda wanda2 = new Wanda();
    }
}

/**
 * 可以，但一个源文件中最多只能有一个公开类（public class）而且文件名必须和公开类的类名完全保持一致。
 */
class TestClass {

}
