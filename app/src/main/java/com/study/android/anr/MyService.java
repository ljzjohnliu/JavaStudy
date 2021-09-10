package com.study.android.anr;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.study.android.utils.Utils;

/**
 * IntentService是Service的子类,由于Service里面不能做耗时的操作,所以Google提供了IntentService,在IntentService内维护了一个工作线程来处理耗时操作，当任务执行完后，IntentService会自动停止。
 * 另外，可以启动IntentService多次，而每一个耗时操作会以工作队列的方式在IntentService的onHandleIntent回调方法中执行，并且，每次只会执行一个工作线程，执行完第一个再执行第二个，以此类推。
 *
 * 如果IntentSerrvice的任务队列完成后会执行Service的onDestroy方法销毁该Service实例，下次有任务再次调用onCreate创建Service；
 * 如果任务队列未执行完成的情况下，再次收到任务会即时放到任务队列，跟之前的任务在同一实例中被执行！
 */
public class MyService extends IntentService {
    private final String TAG = "MyService";

    //这里必须有一个空参数的构造实现父类的构造,否则会报异常
    //java.lang.InstantiationException: java.lang.Class<***.MyService> has no zero argument constructor
    public MyService() {
        super("MyService");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "MyService, onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "MyService, onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.d(TAG, "MyService, onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "MyService, onDestroy");
        super.onDestroy();
    }

    //这个是IntentService的核心方法,它是通过串行来处理任务的,也就是一个一个来处理
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "MyService, onHandleIntent工作线程是: " + Thread.currentThread().getName() + ", getPids = " + Utils.getPids());
        String task = intent.getStringExtra("task");
        Log.d(TAG, "MyService, onHandleIntent任务是 :" + task);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
