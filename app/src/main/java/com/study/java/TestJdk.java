package com.study.java;

import android.annotation.SuppressLint;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicStampedReference;

public class TestJdk {
    public static void main(String[] args) {
        test();
    }

    @SuppressLint("NewApi")
    public static void test() {
        List<String> list = Arrays.asList("aaaa", "bbbb", "cccc");

        //静态方法语法	ClassName::methodName
//        list.forEach(TestJdk::printT);
//        String s1 = "\uD834\uDD1E";
        String s1 = "😀";
        System.out.println("s1 is = " + s1);
        System.out.println("s1 codePointCount is = " + s1.codePointCount(0, s1.length()));
        System.out.println("s1 length is = " + s1.length());
        String s2 = "A";
        System.out.println("s2 length is = " + s2.length());
        String s3 = "韩";
        System.out.println("s3 length is = " + s3.length());
        ConcurrentHashMap map;
    }

    public static void printT(String str) {
        System.out.println("---------" + str);
    }

    public static void printN() {
        System.out.println("**********");
    }
}
