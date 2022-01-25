package com.study.java.other;

class A {

    static {
        System.out.print("1");
    }

    public A() {
        System.out.print("2");
    }
}

class B extends A {

    static {
        System.out.print("a");
    }

    public B() {
        System.out.print("b");
    }
}

public class Hello {

    /**
     * 当Java程序需要使用某个类时，JVM会确保这个类已经被加载、连接（验证、准备和解析）和初始化。
     * 类的加载是指把类的.class文件中的数据读入到内存中，通常是创建一个字节数组读入.class文件，
     * 然后产生与所加载类对应的Class对象。加载完成后，Class对象还不完整，所以此时的类还不可用。
     * 当类被加载后就进入连接阶段，这一阶段包括验证、准备（为静态变量分配内存并设置默认的初始值）
     * 和解析（将符号引用替换为直接引用）三个步骤。
     * 最后JVM对类进行初始化，包括：
     * 1)如果类存在直接的父类并且这个类还没有被初始化，那么就先初始化父类；
     * 2)如果类中存在初始化语句，就依次执行这些初始化语句。
     *
     * 创建对象时构造器的调用顺序是：先初始化静态成员，然后调用父类构造器，再初始化非静态成员，最后调用自身构造器。
     * 注意：静态语句块只会执行一次
     */
    public static void main(String[] args) {
        A ab = new B();
        ab = new B();
    }

}

