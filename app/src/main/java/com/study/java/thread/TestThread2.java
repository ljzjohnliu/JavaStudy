package com.study.java.thread;

public class TestThread2 {

    public static void main(String[] args) {
//        testYield();
        testJoin();
    }

    /**
     * join()的使用场景
     * 在很多情况下，主线程创建并启动子线程，如果子线程中要进行大量的耗时运算，主线程将可能早于子线程结束。如果主线程需要知道子线程的执行结果时，就需要等待子线程执行结束了。
     * 主线程可以sleep(xx),但这样的xx时间不好确定，因为子线程的执行时间不确定，join()方法比较合适这个场景。
     *
     * 1、join与start调用顺序问题
     * join方法必须在线程start方法调用之后调用才有意义。这个也很容易理解：如果一个线程都没有start，那它也就无法同步了。
     * 2、join() 和 sleep() 一样，可以被中断（被中断时，会抛出 InterruptedException 异常）；不同的是，join() 内部调用了 wait()，会出让锁，而 sleep() 会一直保持锁。
     */
    private static void testJoin() {
        Thread thread1 = new Thread("张三") {
            @Override
            public void run() {
                System.out.println("线程 id = " + getId() + ", name = " + getName() + ", getPriority = " + getPriority() +  " 打印信息");
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
            //解释一下，是主线程等待子线程的终止。也就是说主线程的代码块中，如果碰到了t.join()方法，此时主线程需要等待（阻塞），等待子线程结束了(Waits for this thread to die.),才能继续执行t.join()之后的代码块。
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程 id = " + Thread.currentThread().getId() + ", name = " + Thread.currentThread().getName() + ", getPriority = " + Thread.currentThread().getPriority() +  " 打印信息");
    }

    /**
     * Java线程中有一个Thread.yield( )方法，很多人翻译成线程让步。顾名思义，就是说当一个线程使用了这个方法之后，它就会把自己CPU执行的时间让掉，让自己或者其它的线程运行。
     * 需要说明的是，尽管yield方法会放弃cpu时间，使当前线程进入就绪状态，这时候所有的线程会再次抢CPU的时间片，也可能该线程再次抢到了！
     * 另外线程有个优先级的问题，那么手里有优先权的这些线程就一定能抢到CPU使用权吗? 不一定的，他们只是概率上大些，也有可能没特权的抢到了。
     */
    private static void testYield() {
        Thread thread1 = new Thread("张三") {
            @Override
            public void run() {
                System.out.println("线程 id = " + getId() + ", name = " + getName() + ", getPriority = " + getPriority() +  " 打印信息");
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
                    if (i == 3) {
                        this.yield();
                    }
                }
            }
        };
        thread2.start();

//        try {
//            //join方法。该方法主要作用是在该线程中的run方法结束后，才往下执行。
//            thread.join();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        System.out.println("主线程 " + Thread.currentThread().getId() + " 打印信息");
    }
}
