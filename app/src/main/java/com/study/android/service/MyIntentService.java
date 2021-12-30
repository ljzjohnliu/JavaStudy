package com.study.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * https://www.jianshu.com/p/8c4181049564
 * 总结说来就是，开启服务执行onCreate()方法，方法中创建好了一个子线程（HandlerThread），
 * （子线程中创建好了一个Looper对象同时创建好了一个MessageQueue消息队列，然后开启轮询消息队列。），
 * 内部创建好的Handler与子线程中的Looper对象绑定。onCreate只有在服务第一次创建的时候才会调用，
 * 之后每次调用都只会执行onStartCommand方法，在此方法中我们构建好了一个Message对象，并且将传递进来的Intent封装在Message，
 * 一起发送到消息队列中。经过轮询将消息分发到Handler的handleMessage中处理，
 * 此时获取到Message中携带的Intent传递给我们实现好的handleIntent方法中进行任务的处理，处理完毕自动调用StopSelf来结束服务。
 * 在onDestroy方法会把所有消息都给退出。
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";

    /**
     * 由于构造器的继承关系，因为IntentService实现了一个带参数的构造器，所以MyIntentService无法继承默认的缺省构造器
     * 但是Android要求四大组建必须要有无参数的构造器
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //在这里通过intent携带的数据，开进行任务的操作。
        int costTime = -1;
        if (intent != null) {
            costTime = intent.getIntExtra("costTime", 0);
        }
        Log.d(TAG, "onHandleIntent: costTime = " + costTime
                + ", id =  " + Thread.currentThread().getId()
                + ",  " + Thread.currentThread().getName());
        if (costTime > 4) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d(TAG, "onHandleIntent: e = " + e);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
