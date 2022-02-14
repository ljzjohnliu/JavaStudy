package com.study.java;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestCall {
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        //3.使用 FutureTask 类进行包装Callable对象，FutureTask对象封装了Callable对象的call()方法的返回值
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);

        //开启ft线程
        for (int i = 0; i < 21; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量i的值" + i);
            if (i == 20)//i为20的时候创建ft线程
            {
                //4.使用FutureTask对象作为Thread对象的target创建并启动新线程
                new Thread(futureTask, "有返回值的线程FutureTask").start();
            }
        }

        //ft线程结束时，获取返回值
        try {
            //5.调用FutureTask对象的get（）方法获取子线程执行结束后的返回值。
            System.out.println("子线程的返回值：" + futureTask.get());//get()方法会阻塞，直到子线程执行结束才返回
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class MyCallable implements Callable {

        @Override
        public Object call() throws Exception {
            int i = 0;
            for (; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            return i;
        }
    }
}
