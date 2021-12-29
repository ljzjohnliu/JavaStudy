package com.study.java.sub;

import com.study.java.base.Rectangle;

/**
 * java构造函数的继承问题//不能继承
 * （1）子类只继承父类的默认(缺省)构造函数，即无形参构造函数。如果父类没有默认构造函数，那子类不能从父类继承默认构造函数。
 * （2）如果子类有带参数的构造器，那么将不再继承父类的默认（缺省）构造器。
 * （3）在创建对象时，先调用父类默认构造函数对对象进行初始化，然后调用子类自身自己定义的构造函数。
 * （4）如果子类想调用父类的非默认构造函数，则必须使用super来实现。
 * （5）子类必须调用父类的构造函数。可以通过系统自动调用父类的默认构造函数，如果父类没有默认构造函数时，子类构造函数必须通过super调用父类的构造函数。
 */
public class Square2 extends Rectangle {
    /**
     * Square2 必须要有构造函数，因为其父类Rectangle已经没有无形参的默认构造函数了，而有参数构造器不能被继承
     * 并且，必须显示地调用其父类的任意一个构造方法
     */
    public Square2(int w) {
        super(w, w);
        System.out.println("-----Square2 1 args constructor----");
    }

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(1);
        Square2 square2 = new Square2(1);
        Square3 square3 = new Square3();
        //尽管Square4没有显式的构造器，但是从其父类中继承了默认的无参数构造器
        Square4 square4 = new Square4();
    }
}
