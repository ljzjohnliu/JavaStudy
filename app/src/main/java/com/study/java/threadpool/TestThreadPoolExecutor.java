package com.study.java.threadpool;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 如何合理配置线程池大小，一般需要根据任务的类型来配置线程池大小：
 * 如果是CPU密集型任务，就需要尽量压榨CPU，参考值可以设为 NCPU+1
 * 如果是IO密集型任务，参考值可以设置为2*NCPU
 * 当然，这只是一个参考值，具体的设置还需要根据实际情况进行调整，比如可以先将线程池大小设置为参考值，再观察任务运行情况和系统负载、资源利用率来进行适当调整。
 * <p>
 * 线程池常用的 3 种阻塞队列
 * 1. LinkedBlockingQueue
 * 对于 FixedThreadPool 和 SingleThreadExecutor 而言，它们使用的阻塞队列是容量为 Integer.MAX_VALUE 的 LinkedBlockingQueue，
 * 可以认为是无界队列。由于 FixedThreadPool 线程池的线程数是固定的，所以没有办法增加特别多的线程来处理任务，这时就需要 LinkedBlockingQueue
 * 这样一个没有容量限制的阻塞队列来存放任务。这里需要注意，由于线程池的任务队列永远不会放满，所以线程池只会创建核心线程数量的线程，所以此时的最大线程数对线程池来说没有意义，
 * 因为并不会触发生成多于核心线程数的线程。
 * 2. SynchronousQueue
 * 第二种阻塞队列是 SynchronousQueue，对应的线程池是 CachedThreadPool。线程池 CachedThreadPool 的最大线程数是 Integer 的最大值，可以理解为线程数是可以无限扩展的。
 * CachedThreadPool 和上一种线程池 FixedThreadPool 的情况恰恰相反，FixedThreadPool 的情况是阻塞队列的容量是无限的，而这里 CachedThreadPool 是线程数可以无限扩展，
 * 所以 CachedThreadPool 线程池并不需要一个任务队列来存储任务，因为一旦有任务被提交就直接转发给线程或者创建新线程来执行，而不需要另外保存它们。
 * 我们自己创建使用 SynchronousQueue 的线程池时，如果不希望任务被拒绝，那么就需要注意设置最大线程数要尽可能大一些，以免发生任务数大于最大线程数时，
 * 没办法把任务放到队列中也没有足够线程来执行任务的情况。
 * 3. DelayedWorkQueue
 * 第三种阻塞队列是DelayedWorkQueue，它对应的线程池分别是 ScheduledThreadPool 和 SingleThreadScheduledExecutor，这两种线程池的最大特点就是可以延迟执行任务，
 * 比如说一定时间后执行任务或是每隔一定的时间执行一次任务。DelayedWorkQueue 的特点是内部元素并不是按照放入的时间排序，而是会按照延迟的时间长短对任务进行排序，
 * 内部采用的是“堆”的数据结构。之所以线程池 ScheduledThreadPool 和 SingleThreadScheduledExecutor 选择 DelayedWorkQueue，是因为它们本身正是基于时间执行任务的，
 * 而延迟队列正好可以把任务按时间进行排序，方便任务的执行。
 */
public class TestThreadPoolExecutor {

    public static void main(String[] args) {
//        useExecutor1();
//        useSingleThreadExecutor();
//        useCachedThreadPool();
//        useFixedThreadPool();
//        useScheduledThreadPool();
        useWorkStealingPool();
//        useWorkStealingPool2();
    }

    /**
     * java doc中，并不提倡我们直接使用ThreadPoolExecutor，而是使用Executors类中提供的几个静态方法来创建线程池：
     * Executors.newCachedThreadPool();       //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
     * Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池
     * Executors.newFixedThreadPool(int);     //创建固定容量大小的缓冲池
     */
    private static void useExecutor1() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                /*new ArrayBlockingQueue<Runnable>(5)*/new LinkedBlockingQueue<>(1), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

    /**
     * Executors.newSingleThreadExecutor();
     * 创建容量corePoolSize和maximumPoolSize为1的缓冲池，
     * 使用的阻塞队列是LinkedBlockingQueue
     * keepAliveTime = 0
     */
    private static void useSingleThreadExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
        }
        executor.shutdown();
    }

    /**
     * Executors.newCachedThreadPool();
     * 创建容量corePoolSize = 0, maximumPoolSize为Integer.MAX_VALUE的缓冲池；
     * keepAliveTime = 60s 线程空闲超过60秒，就销毁线程；存在OOM风险。
     * 使用的阻塞队列是SynchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。
     */
    private static void useCachedThreadPool() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

    /**
     * Executors.newFixedThreadPool(int);
     * 创建的线程池corePoolSize和maximumPoolSize值是相等的，它使用的LinkedBlockingQueue；
     * keepAliveTime = 0
     */
    private static void useFixedThreadPool() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

    /**
     * Executors.newScheduledThreadPool(int);
     * 创建容量corePoolSize为设定值, maximumPoolSize为Integer.MAX_VALUE的缓冲池
     * 和其他线程池最大的区别是使用的阻塞队列是DelayedWorkQueue，而且多了两个定时执行的方法scheduleAtFixedRate和scheduleWithFixedDelay
     * keepAliveTime是10毫秒
     */
    private static void useScheduledThreadPool() {
        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        for (int i = 0; i < 15; i++) {
            MyTask myTask = new MyTask(i);
            executor.schedule(myTask, (15 - i), TimeUnit.SECONDS);
//            executor.execute(myTask);
            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                    executor.getQueue().size() + "，已执行完别的任务数目：" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

    /**
     * Executors.newWorkStealingPool()
     * JDK8引入，创建持有足够线程的线程池支持给定的并行度，并通过使用多个队列减少竞争。
     */
    @SuppressLint("NewApi")
    private static void useWorkStealingPool() {
        ForkJoinPool executor = (ForkJoinPool) Executors.newWorkStealingPool();

        System.out.println(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < 15; i++) {
//            MyTask myTask = new MyTask(i);
//            executor.execute(myTask);//精灵线程

            CallTask callTask = new CallTask(i);
            Future<Integer> future = executor.submit(callTask);
            System.out.println(i + ", future.isDone() = " + future.isDone());
        }
        try {
            System.in.read();
            //由于产生的是精灵线程（守护线程、后台线程），主程序不阻塞的话看不到打印信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}

class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task " + taskNum);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task " + taskNum + "执行完毕");
    }
}

class CallTask implements Callable<Integer> {

    private Integer number;

    public CallTask(Integer number) {
        this.number = number;
    }

    public Integer call() {
        System.out.println("正在执行task " + number);
        int result = number * 100;
        System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
