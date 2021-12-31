package com.study.java.base;

public class Rectangle extends Shape {

    int w, h;
    /**
     * 默认缺省构造器可以不显示的调用，但是也会先调用父类的无参构造器！！！
     */
    public Rectangle(int w) {
        this(w, w);
        System.out.println("-----Rectangle 1 args constructor----");
    }

    public Rectangle(int w, int h) {
        super();
        this.w = w;
        this.h = h;
        System.out.println("-----Rectangle 2 args constructor----");
    }

    @Override
    public void print() {
        System.out.println("Rectangle w = " + w +  ", h = " + h);
    }

    @Override
    public Shape getSome(Object f) {
        return new Rectangle(100);
    }
}
