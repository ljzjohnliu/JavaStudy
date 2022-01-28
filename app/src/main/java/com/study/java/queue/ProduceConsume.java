package com.study.java.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 采用阻塞队列实现生产者消费者模型
 */
public class ProduceConsume {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
        Producer p = new Producer(queue, "PPPP");
        Consumer c1 = new Consumer(queue, "1111");
        Consumer c2 = new Consumer(queue, "2222");
        new Thread(p).start();
        new Thread(c1).start();
        new Thread(c2).start();
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
        try {
            while (true) {
                queue.put(produce());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    String produce() {
        try {
            Thread.sleep(20);
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
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消费者" + name + ", consume **** is : " + s);
    }
}
