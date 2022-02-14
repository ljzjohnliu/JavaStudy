package com.study.java.leecode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class PrintTest3 {
    static SynchronousQueue<Runnable> queue = new SynchronousQueue();
    static BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(2);

    public static void main(String[] args) {
        printTest();
    }

    public static void printTest() {
        RunnableA runnableA = new RunnableA();
        Runnable1 runnable1 = new Runnable1();
        Thread threadA = new Thread(runnableA);
        Thread thread1 = new Thread(runnable1);
        threadA.start();
        thread1.start();
    }

    static class RunnableA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 26; i++) {
                System.out.println((char) ('A' + i));
            }
        }
    }

    static class Runnable1 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 53; i++) {
                System.out.println(i);
            }
        }
    }
}
