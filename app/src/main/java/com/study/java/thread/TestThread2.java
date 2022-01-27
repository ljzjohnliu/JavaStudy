package com.study.java.thread;

public class TestThread2 {

    private static Object lock = new Object();

    public static void main(String[] args) {
//        testYield();
//        testJoin();
        testSleep();
//        testInterrupt();
    }

    /**
     * Java的中断是一种协作机制。也就是说调用线程对象的interrupt方法并不一定就中断了正在运行的线程，它只是要求线程自己在合适的时机中断自己。
     * 每个线程都有一个boolean的中断状态（这个状态不在Thread的属性上），interrupt方法仅仅只是将该状态置为true。
     * 比如对正常运行的线程调用interrupt()并不能终止他，只是改变了interrupt标示符。
     * 一般说来，如果一个方法声明抛出InterruptedException，表示该方法是可中断的,比如wait,sleep,join，
     * 也就是说可中断方法会对interrupt调用做出响应（例如sleep响应interrupt的操作包括清除中断状态，抛出InterruptedException）,
     * 异常都是由可中断方法自己抛出来的，并不是直接由interrupt方法直接引起的。
     * Object.wait, Thread.sleep方法，会不断的轮询监听 interrupted 标志位，发现其设置为true后，会停止阻塞并抛出 InterruptedException异常。
     */
    private static void testInterrupt() {
        Thread thread1 = new Thread("张三") {
            @Override
            public void run() {
                System.out.println("线程 id = " + getId() + ", name = " + getName() + ", getPriority = " + getPriority() + " 打印信息");
                synchronized (TestThread2.class) {
                    for (int i = 1; i <= 10; i++) {
                        System.out.println("线程 " + getId() + ", name = " + this.getName() + "-----" + i);
                        // 当i为3时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）但是不会让出锁
                        if (i == 3) {
                            try {
                                Thread.sleep(200000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        thread1.start();

        System.out.println("主线程 id = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", getPriority = " + Thread.currentThread().getPriority() + " 打印信息");
        for (int i = 1; i <= 10; i++) {
            System.out.println("线程 " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + "-----" + i);
            if (i == 6) {
//                thread1.interrupt();
            }
        }
    }

    /**
     * Thread.sleep()被用来暂停当前线程的执行,会通知线程调度器把当前线程在指定的时间周期内置为wait状态。
     * 当wait时间结束，线程状态重新变为Runnable并等待CPU的再次调度执行。所以线程sleep的实际时间取决于线程调度器，而这是由操作系统来完成的。
     *
     * 在windows环境下，进程调度是抢占式的。一个进程在运行态时调用sleep()，进入等待态，睡眠结束以后，并不是直接回到运行态，
     * 而是进入就绪队列,要等到其他进程放弃时间片后才能重新进入运行态。所以sleep(1000),在1000ms以后，线程不一定会被唤醒。
     * sleep(0)可以看成一个运行态的进程产生一个中断，由运行态直接转入就绪态。这样做是给其他就绪态进程使用时间片的机会。
     * 总之，还是操作系统中运行态、就绪态和等待态相互转化的问题。
     *
     * 当前线程sleep的情况下，不影响其他线程执行，比如下边例子中thread1进入sleep时候，主线程会获取时间片继续执行
     * 不过使用了同一个锁的情况就不同了，比如给下例子中都加上synchronized (TestThread2.class)，那么主线程中的for循环一定会在子线程for循环执行完，让出类锁才能执行！
     */
    private static void testSleep() {
        Thread thread1 = new Thread("张三") {
            @Override
            public void run() {
                System.out.println("线程 id = " + getId() + ", name = " + getName() + ", getPriority = " + getPriority() + " 打印信息");
                synchronized (TestThread2.class) {
                    for (int i = 1; i <= 10; i++) {
                        System.out.println("线程 " + getId() + ", name = " + this.getName() + "-----" + i);
                        // 当i为3时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                        if (i == 3) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        thread1.start();

        System.out.println("主线程 id = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", getPriority = " + Thread.currentThread().getPriority() + " 打印信息");
        synchronized (TestThread2.class) {
            for (int i = 1; i <= 10; i++) {
                System.out.println("线程 " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + "-----" + i);
                if (i == 6) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * join()的使用场景
     * 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程将可能早于子线程结束。如果主线程需要知道子线程的执行结果时，就需要等待子线程执行结束了。
     * 主线程可以sleep(xx),但这样的xx时间不好确定，因为子线程的执行时间不确定，join()方法比较合适这个场景。也可以在调用join()时带上一个超时参数（单位可以是毫秒，或者毫秒和纳秒），
     * 这样如果目标线程在这段时间内还没有结束的话，join()方法总能返回。对join()方法的调用可以被中断，做法是在调用线程上调用interrupt()方法。
     *
     * 1、join与start调用顺序问题
     * join方法必须在线程start方法调用之后调用才有意义。这个也很容易理解：如果一个线程都没有start，那它也就无法同步了。
     * 如果线程被生成了，但还未被启动，isAlive()将返回false，调用它的join()方法是没有作用的。将直接继续向下执行。
     * 2、join() 和 sleep() 一样，可以被中断（被中断时，会抛出 InterruptedException 异常）；不同的是，join() 内部调用了 wait()，会出让锁，而 sleep() 会一直保持锁。
     */
    private static void testJoin() {
        Thread thread1 = new Thread("张三") {
            @Override
            public void run() {
                System.out.println("线程 id = " + getId() + ", name = " + getName() + ", getPriority = " + getPriority() + " 打印信息");
                for (int i = 1; i <= 10; i++) {
                    System.out.println("线程 " + getId() + ", name = " + this.getName() + "-----" + i);
                    // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                    if (i == 3) {
                        this.yield();
                    }
                }
            }
        };
        thread1.start();

        try {
            //解释一下，是主线程等待子线程的终止。也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），
            // 等待子线程结束了(Waits for this thread to die.),才能继续执行t.join()之后的代码块。
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程 id = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", getPriority = " + Thread.currentThread().getPriority() + " 打印信息");
    }

    /**
     * Java线程中有一个Thread.yield()方法，很多人翻译成线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，让自己或者其它的线程运行。
     * 需要说明的是，尽管yield方法会放弃cpu时间，使当前线程进入就绪状态，这时候所有的线程会再次抢CPU的时间片，也可能该线程再次抢到了！
     * 另外线程有个优先级的问题，那么手里有优先权的这些线程就一定能抢到CPU使用权吗? 不一定的，他们只是概率上大些，也有可能没特权的抢到了。
     */
    private static void testYield() {
        Thread thread1 = new Thread("张三") {
            @Override
            public void run() {
                System.out.println("线程 id = " + getId() + ", name = " + getName() + ", getPriority = " + getPriority() + " 打印信息");
                for (int i = 1; i <= 10; i++) {
                    System.out.println("线程 " + getId() + ", name = " + this.getName() + "-----" + i);
                    // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                    if (i == 3) {
                        this.yield();
                    }
                }
            }
        };
        thread1.start();

        Thread thread2 = new Thread("李四") {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("线程 " + getId() + ", name = " + this.getName() + "-----" + i);
                    // 当i为3时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
                    if (i == 6) {
                        this.yield();
                    }
                }
            }
        };
        thread2.start();

        System.out.println("主线程 " + Thread.currentThread().getId() + " 打印信息");
    }
}
