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
//        strCompare();
        String a3 = "abcdef";
        String a4 = a1 + a2;
        System.out.println(System.identityHashCode(a3));
        System.out.println(System.identityHashCode(a4));
        System.out.println(a3 == a4);//因为a1和a2都是final类型的且在编译阶段都是已经赋值了，所以相当于一个常量，当执行a3=a1+a2;的时候，a3已经是字符串abcdef了，所以相等

        String b3 = "aaabbb";
        String b4 = b1 + b2;
        System.out.println(System.identityHashCode(b3));
        System.out.println(System.identityHashCode(b4));
        System.out.println(b3 == b4);//虽然b1和b2都是final类型的但是一开始没有初始化，在编译期还不可以知道具体的值，还是变量，所以什么时候赋值，赋什么值都是个变数。所以是false。
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
