package com.study.java.thread;

public class ThreadTest implements Runnable{

    @Override
    public void run() {
//        for (int i = 0; i < 5; i++) {
//            System.out.println(Thread.currentThread().getName() + ": for loop: " + i);
//        }

        synchronized (ThreadTest.class) {
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + ": for synchronized loop: " + i);
            }
        }
    }

    public static void main(String[] args) {
        Runnable r1 = new ThreadTest();
        Runnable r2 = new ThreadTest();
        Thread t1 = new Thread(r1, "T1");
        Thread t2 = new Thread(r2, "T2");
        t1.start();
        t2.start();
    }
}
