package com.study.java.leecode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintTest2 {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

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
                lock.lock();
                System.out.println((char)('A' + i));
                condition.signalAll();
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    System.out.println("---------AAAA---------e = " + e);
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }
    }

    static class Runnable1 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 53; i++) {
                lock.lock();
                System.out.println(i);
                condition.signalAll();
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    System.out.println("---------1111---------e = " + e);
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }
    }
}
