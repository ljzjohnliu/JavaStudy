package com.study.java.thread;

public class TestThread3 {

    public static void main(String[] args) {
        MyThread thread1 = new MyThread("火箭1");
        MyRunnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(myRunnable);

        /**
         * Thread用 start方法来启动线程，真正实现了多线程运行，这时无需等待run方法体代码执行完毕而直接继续执行下面的代码。
         * 通过调用Thread类的 start()方法来启动一个线程，这时此线程处于就绪（可运行）状态，并没有运行，一旦得到cpu时间片，就开始执行run()方法，
         * 这里方法 run()称为线程体，它包含了要执行的这个线程的内容，Run方法运行结束，此线程随即终止。
         */
        thread1.start();
        thread2.start();

        /**
         * 以下写法跟上边有啥不同，执行效果会怎么样呢？
         * 总结：调用start方法方可启动线程，而run方法只是thread的一 个普通方法调用，还是在主线程里执行。
         */
        thread1.run();
        thread2.run();

        System.out.println("火箭发射倒计时:");
    }
}

/**
 * Java Thread类实现多线程方法
 * 总结
 * （1）优点：编写简单，如果需要访问当前线程，无需使用Thread.currentThread()方法，直接使用this，即可获得当前线程。
 * （2）缺点：因为线程类已经继承了Thread类，所以不能再继承其他的父类。
 */
class MyThread extends Thread {
    private int countDown = 10;

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println("$" + this.getName() + "(" + countDown + ")");
        }
    }
}

/**
 * Java Runnable接口实现多线程方法
 * 大多数情况下，如果只想重写run() 方法，而不重写其他 Thread 方法，那么应使用Runnable 接口。
 * 这很重要，因为除非程序员打算修改或增强类的基本行为，否则不应为该类创建子类。(推荐使用创建任务类，并实现Runnable接口，而不是继承Thread类)。
 * （1）优点：线程类只是实现了Runable接口，还可以继承其他的类。在这种方式下，可以多个线程共享同一个目标对象，
 * 所以非常适合多个相同线程来处理同一份资源的情况，从而可以将CPU代码和数据分开，形成清晰的模型，较好地体现了面向对象的思想。
 * （2）缺点：编程稍微复杂，如果需要访问当前线程，必须使用Thread.currentThread()方法。
 */
class MyRunnable implements Runnable {
    private int countDown = 10;

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println("$" + Thread.currentThread().getName() + "(" + countDown + ")");
        }
    }
}
