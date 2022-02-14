package com.study.java;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerAndCustomer {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        Producer producer1 = new Producer(queue);
        Customer customer1 = new Customer(queue);
//        new Thread(producer1).start();
//        new Thread(customer1).start();

        Integer a = 128;
        Integer b = 128;
        int q = 128;
        System.out.println(a == b);
    }

    static class Producer implements Runnable {
        BlockingQueue<String> queue;

        public Producer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String str = "Producer : " + (int) (Math.random() * (100));
                    queue.put(str);
                    System.out.println(str);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Customer implements Runnable {
        BlockingQueue<String> queue;

        public Customer(BlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String str = queue.take();
                    System.out.println("Customer str = " + str);
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
