package com.study.java.string;

public class Test {
    /**
     * 解析：
     * 1. String是引用类型，这里 == 比较的是引用是否相同，即是否指向相同的地址
     * 2. 在new String对象时，会产生一个新的对象，并不会使用常量池中的字符串
     * 3. intern会在常量池中寻找该字符串（如果没有责新建），并返回他的地址
     */
    public static void main(String[] args) {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        System.out.println(s1 == s2); // false
        System.out.println(s1 == s5); //true
        System.out.println(s1 == s6); //false
        System.out.println(s1 == s6.intern()); //true
        System.out.println(s2 == s2.intern()); //false

        System.out.println(s1 + null);
        StringBuilder builder = new StringBuilder();
        builder.append("hello").append(1);
        System.out.println(builder.toString());
    }
}
