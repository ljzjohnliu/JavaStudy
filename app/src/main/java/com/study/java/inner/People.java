package com.study.java.inner;

public class People {
    public People() {

    }

    public static void main(String[] args) {
        Man man = new Man();
        People woman = man.getWoman("ljz");
        test(999);
    }

    /**
     * java8中已经没有匿名内部类和局部内部类只能访问final变量的限制了！
     * 之前必须用final修饰int a，不然会报错！！！
     */
    public static void test(int a) {
        new Thread(){
            public void run() {
                System.out.println(a);
            };
        }.start();
    }
}

class Man {
    private int sex = 0;
    public Man() {

    }

    public People getWoman(String name2) {
        String name = "xxx";
        /**
         * 局部内部类
         * 注意，局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
         */
        class Woman extends People {
            public Woman() {
                System.out.println("name2 is : " + name2 + ", sex is : " + sex);
            }
        }
        return new Woman();
    }
}