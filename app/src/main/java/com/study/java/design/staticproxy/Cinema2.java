package com.study.java.design.staticproxy;

public abstract class Cinema2 implements Movie2 {
    @Override
    public void play() {
        System.out.println("----------play----------");
    }

    /**
     * 如果实现了接口的类，不能实现接口中全部方法，那么需要声明成抽象类
     * 抽象类实例化的时候需要实现抽象方法。
     */
    public static void main(String[] args) {
        Cinema2 cinema2 = new Cinema2() {
            @Override
            public void test() {
                System.out.println("----------test----------");
            }
        };
        cinema2.test();
    }
}
