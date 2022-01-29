package com.study.java.lock;

public class WaitNotifyTest {

    private static final User user = new User("ljz", 18);

    public static void main(String[] args) {
        T1 t1 = new T1(user);
        T2 t2 = new T2(user);
        new Thread(t1).start();
        new Thread(t2).start();
    }
}

class T1 implements Runnable {

    private User user;

    public T1(User user) {
        super();
        this.user = user;
    }

    @Override
    public void run() {
        fun1();
        fun2();
        fun3();
    }

    public void fun1() {
        System.out.println("T1 线程执行方法1");
    }

    public void fun2() {
        System.out.println("T1 线程开始执行方法2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("T1 线程执行方法2 sleep 结束");
        synchronized (user) {
            System.out.println("T1 线程执行方法2 获取到对象锁");
            user.notify();
            //尽管这里notify了，但是还是要退出sync语句块才能释放掉锁
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("T1 线程执行方法2 ******* ");
        }
        System.out.println("T1 线程执行方法2结束");
    }

    public void fun3() {
        System.out.println("T1 线程执行方法3");
    }
}

class T2 implements Runnable {
    private User user;

    public T2(User user) {
        super();
        this.user = user;
    }

    @Override
    public void run() {
        funA();
        funB();
        funC();
    }

    public void funA() {
        System.out.println("T2 执行方法A");
    }

    public void funB() {
        System.out.println("T2 开始执行方法B");
        synchronized (user) {
            System.out.println("T2 执行方法B 获取到对象锁！1111");
            try {
                //阻塞住等待notify，并且出让了对象锁！！
                //这也是为啥只有在synchronized中才能执行这个的原因！
                user.wait();
                System.out.println("T2 执行方法B 获取到对象锁！2222");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("T2 执行方法B 获取到对象锁！3333");
            }
        }
        System.out.println("T2 执行方法B结束");
    }

    public void funC() {
        System.out.println("T2 执行方法C");
    }
}