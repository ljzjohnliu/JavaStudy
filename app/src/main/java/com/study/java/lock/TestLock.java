package com.study.java.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        testAtomicReference();
//        testSimpleSpinningLock();
    }

    /* ---------------------悲观锁的使用方式---------------------- */
    public synchronized void testMethod() {

    }

    //这里可以指定是公平锁还是非公平锁
    private ReentrantLock lock = new ReentrantLock(true);//需要保证多个线程使用一个锁

    public void modifyPublicResources() {
        lock.lock();
        // 操作同步资源
        lock.unlock();
    }

    /* ---------------------乐观锁的使用方式---------------------- */
    private AtomicInteger atomicInteger = new AtomicInteger();//保证多个线程使用同一个AtomicInteger

    public void testAtomicInteger() {
        atomicInteger.incrementAndGet();//执行自增1
    }

    public static void testAtomicReference() {
        User user1 = new User("张三", 23);
        User user2 = new User("李四", 25);
        User user3 = new User("王五", 20);
        System.out.println("user1 : " + user1.toString());
        System.out.println("user2 : " + user2.toString());
        System.out.println("user3 : " + user3.toString());

        /**
         * compareAndSet(V expect, V update)
         * 该方法作用是：如果atomicReference==expect，就把update赋给atomicReference，否则不做任何处理。
         */
        //初始化为 user1
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(user1);

        //把 user2 赋给 atomicReference 成功 因为当前的值就是user1跟预期一致！
        atomicReference.compareAndSet(user1, user2);
        System.out.println(atomicReference.get().toString());

        //把 user3 赋给 atomicReference 成功 因为当前的值就是user2跟预期不一致！
        atomicReference.compareAndSet(user1, user3);
        System.out.println(atomicReference.get().toString());
    }

    /**
     * 自定义 SimpleSpinningLock 实现锁功能
     */
    public static void testSimpleSpinningLock() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        SimpleSpinningLock simpleSpinningLock = new SimpleSpinningLock();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    simpleSpinningLock.lock();
                    ++count;
                    simpleSpinningLock.unLock();
                    countDownLatch.countDown();
                }
            });

        }
        System.out.println("等待打印count----");
        countDownLatch.await();
        // 多次执行输出均为：10 ，实现了锁的基本功能
        System.out.println(count);
        executorService.shutdown();
    }
}