package com.study.java.sub;

import com.study.java.base.Rectangle2;

public class Square3 extends Rectangle2 {

    /**
     * 默认缺省构造器可以不显示的调用，但是也会先调用父类的无参构造器！！！
     */
    public Square3() {
        System.out.println("-----Square3 0 args constructor----");
    }
}
