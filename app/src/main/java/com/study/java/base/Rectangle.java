package com.study.java.base;

public class Rectangle extends Shape {

    /**
     * 默认缺省构造器可以不显示的调用，但是也会先调用父类的无参构造器！！！
     */
    public Rectangle(int w) {
        System.out.println("-----Rectangle 1 args constructor----");
    }

    public Rectangle(int w, int h) {
        super();
        System.out.println("-----Rectangle 2 args constructor----");
    }
}
