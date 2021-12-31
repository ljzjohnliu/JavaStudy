package com.study.java.base;

public class Shape {

    public Shape() {
        System.out.println("-----Shape 0 args constructor----");
    }

    public void draw() {
        System.out.println("draw shape!");
    }

    public void print() {
        System.out.println("Shape.print()");
        draw();
    }

    public Shape getSome(Object f) {
        return new Shape();
    }

    public static Object getObj() {
        return "Obj";
    }
}
