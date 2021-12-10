package com.study.java.reflect;

public class Student extends Person {
    public int num;
    private int id;

    public void testNum() {
        System.out.println("-----num is = " + num);
    }

    private void testId() {
        System.out.println("-----id is = " + id);
    }
}
