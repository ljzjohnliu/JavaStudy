package com.study.java.base;

public class Shap {

    public void draw() {
        System.out.println("draw shape!");
    }

    public void print() {
        System.out.println("Shape.print()");
        draw();
    }

    public static Object getObj() {
        return "Obj";
    }
}
