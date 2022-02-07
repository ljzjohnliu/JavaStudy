package com.study.java.leecode;

public class PrintTest {
    public static void main(String[] args) {
        printTest();
    }

    public static void printTest() {
        Object object = new Object();
        RunnableA runnableA = new RunnableA(object);
        Runnable1 runnable1 = new Runnable1(object);
        Thread threadA = new Thread(runnableA);
        Thread thread1 = new Thread(runnable1);
        threadA.start();
        thread1.start();
    }

    static class RunnableA implements Runnable {
        Object obj;

        RunnableA(Object obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            for (int i = 0; i < 26; i++) {
                System.out.println("---------AAAA---------");
                synchronized (obj) {
                    System.out.println((char)('A' + i));
                    obj.notifyAll();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Runnable1 implements Runnable {
        Object obj;

        Runnable1(Object obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            for (int i = 0; i < 53; i++) {
                System.out.println("---------1111---------");
                synchronized (obj) {
                    System.out.println(i);
                    obj.notifyAll();
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
