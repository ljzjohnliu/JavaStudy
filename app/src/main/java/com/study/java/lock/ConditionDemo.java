package com.study.java.lock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    volatile int key = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();
        new Thread(demo.new A()).start();
        new Thread(demo.new B()).start();

        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    class A implements Runnable {
        @Override
        public void run() {
            int i = 10;
            while (i > 0) {
                lock.lock();
                try {
                    System.out.println("A is Running key = " + key);
                    if (key == 1) {
                        i--;
                        key = 0;
                        condition.signal();
                    } else {
                        condition.awaitUninterruptibly();
                    }

                } finally {
                    lock.unlock();
                }
            }
        }

    }

    class B implements Runnable {
        @Override
        public void run() {
            int i = 10;
            while (i > 0) {
                lock.lock();
                try {
                    System.out.println("B is Running key = " + key);
                    if (key == 0) {
                        i--;
                        key = 1;
                        condition.signal();
                    } else {
                        condition.awaitUninterruptibly();
                    }

                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
