package com.study.java.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 采用阻塞队列实现生产者消费者模型
 * 阻塞队列的几个api
 * 1、会抛出异常的三个方法
 * add:将非空元素加入队列中，如果能够容纳，返回true，否则trows IllegalStateException
 * remove: 移除队列头部元素，如果队列为空，trows NoSuchElementException
 * element: 取出队列头部元素，如果队列为空，trows exception
 *
 * 2、返回值
 * offer: 将非空元素加入队列中,如果BlockingQueue可以容纳,则返回true,否则返回false.
 * poll: 移除队列头部元素，如果队列为空，返回null
 * peek: 取出队列头部元素，如果队列为空，返回null
 *
 * 3.blocks
 * put: 添加元素到队列，如果队列已满,线程进入等待，直到有空间
 * take: 移除队列头部元素，如果队列为空,线程进入等待，直到有新的数据加入
 *
 * 4.Times Out
 * offer (E e, long timeout, TimeUnit unit): 将非空元素加入队列中,在等待的时间内如果可以容纳，返回true,否则返回false
 * poll (long timeout, TimeUnit unit): 移除队列头部元素，在等待的时间内如果队列为空，返回null
 */
public class ProduceConsume {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        Producer p = new Producer(queue, "PPPP");
        Consumer c1 = new Consumer(queue, "1111");
        Consumer c2 = new Consumer(queue, "2222");
//        new Thread(p).start();
        try {
            queue.put("AAAA");
            queue.put("BBBB");
            queue.put("CCCC");
            queue.put("DDDD");
            queue.put("EEEE");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> list = new ArrayList<>();
        queue.drainTo(list, 6);
        for (String str : list) {
            System.out.println("str = " + str);
        }
//        new Thread(c1).start();
//        new Thread(c2).start();
    }
}

class Producer implements Runnable {

    private final BlockingQueue<String> queue;
    private String name;

    Producer(BlockingQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        //以下写法是为了验证阻塞队列的几个api用法
//        while (true) {
//            boolean is = queue.offer(produce());
////            queue.remove();
//            System.out.println("----BlockingQueue---size = " + queue.size() + ", isSuccess = " + is);
//        }
        try {
            while (true) {
                queue.put(produce());
                System.out.println("----BlockingQueue---size = " + queue.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    String produce() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String proStr = "someString : " + (int)(Math.random() * 100);
        System.out.println("生产者" + name + ", produce ---- is : " + proStr);
        return proStr;
    }
}

class Consumer implements Runnable {
    private final BlockingQueue<String> queue;
    private String name;
    Consumer(BlockingQueue queue, String name) {
        this.queue = queue;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void consume(String s) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消费者" + name + ", consume **** is : " + s);
    }
}
