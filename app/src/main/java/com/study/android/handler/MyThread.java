package com.study.android.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.study.android.utils.Utils;

public class MyThread extends Thread {
    private static final String TAG = "MyThread";

    public Handler mHandler = null;
    private Looper mLooper;

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        //只有在prepare后再次Looper.myLooper()才能获取到当前线程的looper，不然会返回主线程的looper！！！
        Looper.prepare();
        //写法一
//        if (mLooper == null) {
//            mLooper = Looper.myLooper();
//        }
        //写法二，参考HandlerThread写法
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Log.d(TAG, "MyThread, run " + Utils.getPids() + ", Looper.myLooper() = " + Looper.myLooper());
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {//问题：它运行在哪个线程,这个是运行在其绑定的Looper对应的线程中的
                Log.d(TAG, "MyThread, mHandler handleMessage " + Utils.getPids());
                Log.d(TAG, "MyThread, mHandler handleMessage 获得了message msg = " + msg);
            }
        };
        Looper.loop();
        //注意：loop之后的代码不会被执行，因为loop会阻塞住无消息的时候
    }

    /**
     * 在Thread的构造函数中调用Looper.myLooper只会得到主线程的Looper，因为此时新线程还未构造好
     */
    public Looper getLooper() {
        // If the thread has been started, wait until the looper has been created.
        synchronized (this) {
            while (mLooper == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        //这里Looper.myLooper()获取到的是mainLooper
        Log.d(TAG, "getLooper: mLooper = " + mLooper + ", looper = " + Looper.myLooper());
        return mLooper;
//        return Looper.myLooper();//这样写法很大可能获取到的是主线的looper，并不能实现绑定子线程looper的Handler！
    }
}
