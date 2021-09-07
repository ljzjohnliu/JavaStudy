package com.study.java.inner;

public class People {
    public People() {

    }

    public static void main(String[] args) {

    }
}

class Man {
    public Man() {

    }

    public People getWoman() {
        /**
         * 局部内部类
         * 注意，局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
         */
        class Woman extends People {
            public Woman() {

            }
        }
        return new Woman();
    }
}