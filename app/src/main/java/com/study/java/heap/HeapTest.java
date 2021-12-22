package com.study.java.heap;

import org.openjdk.jol.info.ClassLayout;

public class HeapTest {

    public static void main(String[] args) {
        String o1 = "Hello";
        String o2 = new String("Hello");
        System.out.println(o1 == o2);
        synchronized (o1) {
            synchronized (o2) {
                System.out.println(ClassLayout.parseInstance(o1).toPrintable());
                System.out.println(ClassLayout.parseInstance(o2).toPrintable());
            }
        }

        String m = "hello,world";
        String n = "hello,world";
        String u = new String(m);
        String v = new String("hello,world");
        System.out.println(System.identityHashCode(m));
        System.out.println(System.identityHashCode(n));
        System.out.println(System.identityHashCode(u));
        System.out.println(System.identityHashCode(v));

        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        /**
         * java中hashCode和identityHashCode的区别
         * 1、hashCode方法和equals方法都是Object类里的方法，其他类的是可以重写的，所以用的时候一定要弄清楚有没有被重写，不然很容易弄错
         * 2、Object类中equals方法只有引用变量指向同一个对象时才返回true，而String类中放宽了要求，对象里的值相等也返回true
         * 3、Object类中的hashCode方法会返回一个hash码，只有指向同一个对象的引用变量调用才会返回相同值，而String类中放宽了要求，对象里的值相等也返回相同值
         * 4、identityHashCode方法是System类中的方法，调用该方法时，不管类中是否重写了Object类中的hashCode方法，都执行Object类中的hashCode方法，返回一个hashCode值。所以只有指向同一个对象的引用变量调用才会返回相同值
         * identityHashCode永远返回根据对象物理内存地址产生的hash值，所以每个String对象的物理地址不一样，identityHashCode也会不一样
         */
        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(System.identityHashCode(s3));
        System.out.println(System.identityHashCode(s4));
        System.out.println(System.identityHashCode(s5));
        System.out.println(System.identityHashCode(s6));
        System.out.println(System.identityHashCode(s9));
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s4);
        System.out.println(s1 == s9);
        System.out.println(s4 == s5);
        System.out.println(s1 == s6);
        /**
         * 解释说明：
         * System.out.println(s1 == s2);  // true
         * System.out.println(s1 == s3);  // true
         * System.out.println(s1 == s4);  // false
         * System.out.println(s1 == s9);  // false
         * System.out.println(s4 == s5);  // false
         * System.out.println(s1 == s6);  // true
         * s1在创建对象的同时，在字符串池中也创建了其对象的引用。
         * 由于s2也是利用字面量创建，所以会先去字符串池中寻找是否有相等的字符串，显然s1已经帮他创建好了，它可以直接使用其引用。那么s1和s2所指向的都是同一个地址，所以s1==s2。
         * s3是一个字符串拼接操作，参与拼接的部分都是字面量，编译器会进行优化，在编译时s3就变成“Hello”了，所以s1==s3。
         * s4虽然也是拼接，但“lo”是通过new关键字创建的，在编译期无法知道它的地址，所以不能像s3一样优化。所以必须要等到运行时才能确定，必然新对象的地址和前面的不同。
         * 同理，s9由两个变量拼接，编译期也不知道他们的具体位置，不会做出优化。
         * s5是new出来的，在堆中的地址肯定和s4不同。
         * s6利用intern()方法得到了s5在字符串池的引用，并不是s5本身的地址。由于它们在字符串池的引用都指向同一个“Hello”对象，自然s1==s6
         *
         * 总结一下：
         * 字面量创建字符串会先在字符串池中找，看是否有相等的对象，没有的话就在堆中创建，把地址驻留在字符串池；有的话则直接用池中的引用，避免重复创建对象。
         * new关键字创建时，前面的操作和字面量创建一样，只不过最后在运行时会创建一个新对象，变量所引用的都是这个新对象的地址。
         * 由于不同版本的JDK内存会有些变化，JDK1.6字符串常量池在永久代，1.7移到了堆中，1.8用元空间代替了永久代。但是基本对上面的结论没有影响，思想是一样的。
         */
    }
}
