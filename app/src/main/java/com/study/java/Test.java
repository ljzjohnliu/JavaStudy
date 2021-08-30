package com.study.java;

public class Test {

    public static void main(String[] args) {
        try {
            String msg = testBack(-1);
            System.out.println("testBack is : " + msg);
        } catch (Exception e) {
            System.out.println("testBack e : " + e);
        }
    }

    public static String testBack(int a) {
        System.out.println("testBack --------1111------- a = " + a);
        if (a > 0) {
            System.out.println("testBack --------2222------- a = " + a);
            return String.valueOf(a);
        } else if (a < 0){
            System.out.println("testBack --------3333------- a = " + a);
            throw new UnsupportedOperationException("Unsupported Api class Type");
        } else {
            System.out.println("testBack --------5555------- a = " + a);
            return "";
        }
    }
}
