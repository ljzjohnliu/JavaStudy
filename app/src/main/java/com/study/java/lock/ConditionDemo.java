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
    Lock l = new ReentrantLock();
    Condition c = l.newCondition();

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();
        new Thread(demo.new A()).start();
        new Thread(demo.new B()).start();

        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
    }

    public static int getData(String param) {
        if (param == null ) {
            return 0;
        }

        HashMap<Integer, String> map = new HashMap<>();
        ConcurrentHashMap map1 = new ConcurrentHashMap();
        map.put(1, "");
        int[] index = new int[128];
        Arrays.fill(index, -1);
        int start = 0;
        int result = 0;
        for (int i = 0; i < param.length(); i++){
           start = Math.max(index[param.charAt(i)] + 1, start);
           result = Math.max(result, i - start + 1);
           index[param.charAt(i)] = i;
        }
        return result;
    }

    class A implements Runnable {
        @Override
        public void run() {
            int i = 10;
            while (i > 0) {
                l.lock();
                try {
                    if (key == 1) {
                        System.out.println("A is Running");
                        System.out.println("A is Running");
                        i--;
                        key = 0;
                        c.signal();
                    } else {
                        c.awaitUninterruptibly();
                    }

                } finally {
                    l.unlock();
                }
            }
        }

    }

    class B implements Runnable {
        @Override
        public void run() {
            int i = 10;
            while (i > 0) {
                l.lock();
                try {
                    if (key == 0) {
                        System.out.println("B is Running");
                        i--;
                        key = 1;
                        c.signal();
                    } else {
                        c.awaitUninterruptibly();
                    }

                } finally {
                    l.unlock();
                }
            }
        }
    }
}
