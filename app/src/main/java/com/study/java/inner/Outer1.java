package com.study.java.inner;

public class Outer1 {

    private Inner1 inner;
    private String name;
    public static int count = 1;

    public Outer1(String name) {
        this.name = name;
    }

    public Inner1 getInnerInstance() {
        if(inner == null)
            inner = new Inner1();
        return inner;
    }

    /**
     * 成员内部类是最普通的内部类，它的定义为位于另一个类的内部
     * 类Inner1像是类Outer1的一个成员，Outer1称为外部类。成员内部类可以无条件访问外部类的所有成员属性和成员方法（包括private成员和静态成员）。
     * 内部类可以拥有private访问权限、protected访问权限、public访问权限及包访问权限。
     * 如果成员内部类Inner用private修饰，则只能在外部类的内部访问，
     * 如果用public修饰，则任何地方都能访问；
     * 如果用protected修饰，则只能在同一个包下或者继承外部类的情况下访问；
     * 如果是默认访问权限，则只能在同一个包下访问。
     * 这一点和外部类有一点不一样，外部类只能被public和包访问两种权限修饰。
     * 由于成员内部类看起来像是外部类的一个成员，所以可以像类的成员一样拥有多种权限修饰。
     */
    private class Inner1 {
        public void test() {
            System.out.println(name);  //外部类的private成员
            System.out.println(count);   //外部类的静态成员
        }
    }

    public static void main(String[] args) {
        /**
         * 第一种方式：
         * 成员内部类实例必须通过Outer1对象来创建
         */
        Outer1 outer1 = new Outer1("outer 1!!!");
        Outer1.Inner1 inner1 = outer1.new Inner1();
        inner1.test();
        //第二种方式
        Outer1.Inner1 inner2 = outer1.getInnerInstance();
    }
}
