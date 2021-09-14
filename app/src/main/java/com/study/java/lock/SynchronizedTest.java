package com.study.java.lock;

/**
 * synchronized同步锁
 * 在java中，每一个对象有且仅有一个同步锁。这也意味着，同步锁是依赖于对象而存在。
 * 当我们调用某对象的synchronized方法时，就获取了该对象的同步锁。例如synchronized(obj)就获取了“obj这个对象”的同步锁。
 *
 * 原理
 * 当我们调用某对象的synchronized方法时，就获取了该对象的同步锁。不同线程对同步锁的访问是互斥的，
 * 也就是说，某时间点，对象的同步锁只能被一个线程获取到！通过同步锁，我们就能在多线程中，实现对“对象/方法”的互斥访问。
 * 例如，现在有两个线程A和线程B，它们都会访问“对象obj的同步锁”。假设，在某一时刻，线程A获取到“obj的同步锁”并在执行一些操作；
 * 而此时，线程B也企图获取“obj的同步锁” —— 线程B会获取失败，它必须等待，直到线程A释放了“该对象的同步锁”之后线程B才能
 * 获取到“obj的同步锁”从而才可以运行。
 *
 * synchronized同步锁三种用法：
 * 1. 修饰普通方法,对普通方法同步
 * 2. 修饰静态方法,静态方法（类）同步
 * 3. 修饰代码块,代码块同步
 * 4. 修饰一个类,对类同步
 *
 * 总结
 * 1、当多个线程同时执行synchronized(x){}同步代码块时呈同步效果。当其他线程执行x对象中的synchronized同步方法时呈同步效果。当其他线程执行x对象方法中的synchronized(this)代码块时也呈同步效果。
 * 2. 无论synchronized关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；
 * 如果synchronized作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁。
 * 3. 每个对象只有一个锁（lock）与之相关联，谁拿到这个锁谁就可以运行它所控制的那段代码。
 * 4. 实现同步是要很大的系统开销作为代价的，甚至可能造成死锁，所以尽量避免无谓的同步控制。
 */
public class SynchronizedTest {

    /**
     * 修饰静态方法,静态方法（类）同步
     * 修饰一个静态的方法，其作用的范围是整个静态方法，作用的对象是这个类的所有对象。
     */
    public static synchronized void staticMethod1(){
        System.out.println("Static Method 1 start");
        try {
            System.out.println("Static Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Static Method 1 end");
    }

    public static synchronized void staticMethod2(){
        System.out.println("Static Method 2 start");
        try {
            System.out.println("Static Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Static Method 2 end");
    }

    /**
     * 修饰普通方法,对普通方法同步
     * 修饰一个普通方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象。
     */
    public synchronized void method1() {
        System.out.println("Method 1 start");
        try {
            System.out.println("Method 1 execute");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 1 end");
    }

    public synchronized void method2() {
        System.out.println("Method 2 start");
        try {
            System.out.println("Method 2 execute");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 2 end");
    }

    /**
     * 修饰代码块,代码块同步
     * 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是大括号{}括起来的代码，作用的对象是调用这个代码块的对象。
     * 这里有一个知识点：
     * synchronized (this)使用的是对象锁，
     * synchronized (类名.class)使用的是类锁
     */
    public void method3(){
        System.out.println("Method 3 start");
        try {
            System.out.println("Method 3 this lock = " + this + ", class lock = " + SynchronizedTest.class);
            synchronized (this) {
                System.out.println("Method 3 execute");
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 3 end");
    }

    public void method4(){
        System.out.println("Method 4 start");
        try {
            synchronized (this) {
                System.out.println("Method 4 execute");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Method 4 end");
    }

    public static void main(String[] args) {
//        testStaticSync();
//        testNormalSync();
//        testBlockSync();
        testBlockSync2();
//        testClassSync();
    }

    /**
     * 验证synchronized修饰静态方法,静态方法（类）同步
     */
    private static void testStaticSync() {
        final SynchronizedTest test = new SynchronizedTest();
        final SynchronizedTest test2 = new SynchronizedTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.staticMethod1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test2.staticMethod2();
            }
        }).start();
    }

    /**
     * 验证synchronized修饰普通方法,对普通方法同步
     */
    private static void testNormalSync() {
        final SynchronizedTest test3 = new SynchronizedTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test3.method1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test3.method2();
            }
        }).start();
    }

    private static void testBlockSync() {
        final SynchronizedTest test = new SynchronizedTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method3();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method4();
            }
        }).start();
    }

    /**
     * 这个可以验证method3，method4中加的锁是对象锁，而非类锁！！！
     */
    private static void testBlockSync2() {
        final SynchronizedTest test = new SynchronizedTest();
        final SynchronizedTest test2 = new SynchronizedTest();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test.method3();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                test2.method4();
            }
        }).start();
    }

    private static void testClassSync() {
        final SyncThread syncThread1 = new SyncThread();
        final SyncThread syncThread2 = new SyncThread();

        new Thread(syncThread1).start();
        new Thread(syncThread2).start();
    }
}

/**
 * 同步线程
 */
class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
        count = 0;
    }

    public static void method() {
        //synchronized作用于一个类T时，是给这个类T加锁，T的所有对象用的是同一把锁
        synchronized(SyncThread.class) {
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void run() {
        method();
    }
}