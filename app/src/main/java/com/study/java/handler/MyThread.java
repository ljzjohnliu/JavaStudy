package com.study.java.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.study.java.utils.Utils;

public class MyThread extends Thread {
    private static final String TAG = "MyThread";

    public Handler mHandler = null;
    private Looper mLooper;

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        Looper.prepare();
        //写法一
//        if (mLooper == null) {
//            mLooper = Looper.myLooper();
//        }
        //写法二，参考HandlerThread写法
//        synchronized (this) {
//            mLooper = Looper.myLooper();
//            notifyAll();
//        }
        Log.d(TAG, "MyThread, run " + Utils.getPids());
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {//问题：它运行在哪个线程
                Log.d(TAG, "MyThread, mHandler handleMessage " + Utils.getPids());
                Log.d(TAG, "MyThread, mHandler handleMessage 获得了message msg = " + msg);
            }
        };
        Looper.loop();
    }

    /**
     * 在Thread的构造函数中调用Looper.myLooper只会得到主线程的Looper，因为此时新线程还未构造好
     */
    public Looper getLooper() {
        // If the thread has been started, wait until the looper has been created.
//        synchronized (this) {
//            while (mLooper == null) {
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                }
//            }
//        }
        //这里Looper.myLooper()获取到的是mainLooper
        Log.d(TAG, "getLooper: mLooper = " + mLooper + ", looper = " + Looper.myLooper());
        return Looper.myLooper();
    }
}
