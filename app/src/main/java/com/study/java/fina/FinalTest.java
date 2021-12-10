package com.study.java.fina;

public class FinalTest {
    public final int A = 10;
    public final int B;

    {
        B = 20;
    }

    public static final int STATIC_C = 30;
    public static final int STATIC_D;

    static {
        STATIC_D = 40;
    }

    public final int E;

    public FinalTest(int var) {
        E = var;
    }

    public static void main(String[] args) {
        FinalTest finalTest = new FinalTest(100);
        System.out.println(" E = " + finalTest.E);

        SubClass sc = new SubClass();
        sc.testFinal();
    }
}
