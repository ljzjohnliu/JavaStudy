package com.study.java.thread;

public class TestThread1 {

    /**
     * 如下有四个子线程和一个主线程，反复运行会发现他们执行顺序是随机的，该怎么解释该现象呢？
     * 进程：是一个正在执行中的程序。每一个进程执行都有一个执行顺序。该顺序是一个执行路径，或者叫一个控制单元。
     * 线程：就是进程中的一个独立的控制单元。线程在控制着进程的执行。一个进程中至少有一个线程。
     * 可以理解为进程的多条执行线索，每条线索又对应着各自独立的生命周期。线程是进程的一个实体,
     * 是CPU调度和分派的基本单位,它是比进程更小的能独立运行的基本单位。一个线程可以创建和撤销另一个线程，同一个进程中的多个线程之间可以并发执行。
     *
     * 线程都出于就绪状态时候，获取到CPU的时间片时候，才会执行，多线程依次获取cpu的使用，才在表象上呈现出来并行！
     */
    public static void main(String[] args) {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("子线程1 " + Thread.currentThread().getId() + " 打印信息");
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                System.out.println("子线程2 " + Thread.currentThread().getId() + " 打印信息");
            }
        };
        Thread thread3 = new Thread() {
            @Override
            public void run() {
                System.out.println("子线程3 " + Thread.currentThread().getId() + " 打印信息");
            }
        };
        Thread thread4 = new Thread() {
            @Override
            public void run() {
                System.out.println("子线程4 " + Thread.currentThread().getId() + " 打印信息");
            }
        };

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        System.out.println("主线程 " + Thread.currentThread().getId() + " 打印信息");
    }
}
