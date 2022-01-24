package com.study.java.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestFailFast {

    public static void main(String[] args) {
//        testFailFast();
//        solveFailFast();
        test();
    }

    /**
     * 当多个线程对同一个集合进行操作的时候，某线程访问集合的过程中，该集合的内容被其他线程所改变(即其它线程通过add、remove、clear等方法，
     * 改变了modCount的值)；这时，就会抛出ConcurrentModificationException异常，产生fail-fast事件。
     */
    private static void testFailFast() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            list.add("new data : " + i);
        }

        Runnable runnable1 = () -> {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.println("print data : " + iterator.next());
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                list.add("add new data 101!");
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }

    private static void solveFailFast() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i <= 100; i++) {
            list.add("new data : " + i);
        }

        Runnable runnable1 = () -> {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                System.out.println("print data : " + iterator.next());
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                list.add("add new data 101!");
                System.out.println(list.size());
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();
        System.out.println(list.size());
    }

    /**
     * CopyOnWriteArrayList原理
     * 向容器添加或删除元素的时候，不直接往当前容器添加删除，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加删除元素，添加删除完元素之后，
     * 再将原容器的引用指向新的容器，整个过程加锁，保证了写的线程安全。
     * CopyOnWriteArrayList是开发过程中常用的一种并发容器，多用于读多写少的并发场景。但是CopyOnWriteArrayList真的能做到完全的线程安全吗？
     * 答案是并不能。
     * 数组越界
     * 但想象一下如果这时候有第三个线程进行删除元素操作，读线程去读取容器中最后一个元素，读之前的时候容器大小为i，当去读的时候删除线程突然删除了一个元素，
     * 这个时候容器大小变为了i-1，读线程仍然去读取第i个元素，这时候就会发生数组越界。
     */
    static List<String> list = new CopyOnWriteArrayList<>();
    public static void test() {
        for (int i = 0; i < 10000; i++) {
            list.add("string" + i);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (list.size() > 0) {
                        String content = list.get(list.size() - 1);
                    } else {
                        break;
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (list.size() <= 0) {
                        break;
                    }
                    list.remove(0);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
