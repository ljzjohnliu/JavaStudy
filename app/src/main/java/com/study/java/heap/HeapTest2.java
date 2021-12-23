package com.study.java.heap;

public class HeapTest2 {

    public static final String a1 = "abc";
    public static final String a2 = "def";

    public static final String b1;
    public static final String b2;

    static {
        b1 = "aaa";
        b2 = "bbb";
    }

    public static void main(String[] args) {

        String s = new String("1");
        String intern = s.intern();
        String s2 = "1";
        System.out.println("s : " + System.identityHashCode(s));
        System.out.println("intern : " + System.identityHashCode(intern));
        System.out.println("s2 : " + System.identityHashCode(s2));
        System.out.println(s2 == intern);
        System.out.println(s == intern);

        System.out.println("------------111111-------------");

//        strCompare();
        String a3 = "abcdef";
        String a4 = a1 + a2;
        System.out.println("a3 : " + System.identityHashCode(a3));
        System.out.println("a4 : " + System.identityHashCode(a4));
        System.out.println(a3 == a4);//因为a1和a2都是final类型的且在编译阶段都是已经赋值了，所以相当于一个常量，当执行a3=a1+a2;的时候，a3已经是字符串abcdef了，所以相等

        String b3 = "aaabbb";
        String b4 = b1 + b2;
        System.out.println("b3 : " + System.identityHashCode(b3));
        System.out.println("b4 : " + System.identityHashCode(b4));
        System.out.println(b3 == b4);//虽然b1和b2都是final类型的但是一开始没有初始化，在编译期还不可以知道具体的值，还是变量，所以什么时候赋值，赋什么值都是个变数。所以是false。

        /**
         * 第一个比较，通过字符串拼接在堆内存中生成值为“aaaccc”的对象，返回c1对象的引用但并未在常量池中生成“aaaccc”对象，
         * s1.intern()在堆中copy一份"aaaccc"引用地址对象到常量池,所以true
         *
         * 第二个比较，因为“aaabbb”在常量池中本身就存在，所以在堆中生成“aaabbb”的对象，返回d1的引用地址，d1.intern()去常量池中检查到（equals（）判断）该对象存在，
         * 所以返回常量池中对象的引用地址。所以为false
         */
        String c1 = new StringBuilder().append("aaa").append("ccc").toString();
        String c2 = new StringBuilder().append("aaa").append("ccc").toString();
        System.out.println("c1 : " + System.identityHashCode(c1));
        System.out.println("c2 : " + System.identityHashCode(c2));
        System.out.println("c1 intern : " + System.identityHashCode(c1.intern()));
        System.out.println("c2 intern : " + System.identityHashCode(c2.intern()));
        System.out.println(c1.intern() == c1);
        System.out.println(c2.intern() == c2);

        String d1 = new StringBuilder().append("aaa").append("bbb").toString();
        System.out.println("d1 : " + System.identityHashCode(d1));
        System.out.println("d1 intern : " + System.identityHashCode(d1.intern()));
        System.out.println(d1.intern() == d1);
    }

    public  static void strCompare() {
        String s1 = "abc1";
        String s2 = "abc" + 1;
        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));
        System.out.println(s1 == s2);//因为字符串abc和数字1都是字面量，所以加起来还是个字面量，又因为常量池中已经有了s1指向的字面量abc1,所以s2也是指向了字面量abc1
        String s3 = "ab";
        String s4 = "c";
        String s5 = "abc";
        String s6 = s3 + s4;
        System.out.println(System.identityHashCode(s5));
        System.out.println(System.identityHashCode(s6));
        System.out.println(s5 == s6);//s3 s4 在编译期还不可以知道具体的值，还是变量，所以什么时候赋值，赋什么值都是个变数，所以比较的话是false
    }
}
