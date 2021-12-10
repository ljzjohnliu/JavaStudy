package com.study.java.throwable;

public class Parent {
    public void testNoException() {
        System.out.println("Parent have no Exception!");
    }

    public void testHasException() throws Exception {
        System.out.println("Parent have Exception!");
    }
}
