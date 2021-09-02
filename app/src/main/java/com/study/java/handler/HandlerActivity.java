package com.study.java.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.java.R;
import com.study.java.utils.Utils;

public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    //自定义Handler子类（继承Handler类） & 复写handleMessage（）方法
    class MyHandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "MyHandler, handleMessage: " + Utils.getPids());
            Log.d(TAG, "MyHandler, handleMessage: msg = " + msg);
        }
    }

    class MyHandler2 extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "MyHandler2, handleMessage: " + Utils.getPids());
            Log.d(TAG, "MyHandler2, handleMessage: msg = " + msg);
            switch (msg.what) {
                case 2:
                    testTv.setText((String)msg.obj);
                default:
                    break;
            }
        }
    }

    /**
     * 方式1：新建Handler子类（内部类）
     * 创建与主线程关联的Handler
     * 以下操作是两个Handler都绑定在了主线程上，使用的是同一个Looper
     * 1个线程（Thread）只能绑定 1个循环器（Looper），但可以有多个处理者（Handler）
     * 1个循环器（Looper） 可绑定多个处理者（Handler）
     * 1个处理者（Handler） 只能绑定1个1个循环器（Looper）
     * 尽管mainHandler和mainHandler2共用了一个Looper以及Message Queue，Looper取出消息的时候，会把消息发送给创建该消息的处理者Handler
     */
    private Handler mainHandler = new MyHandler();
    private Handler mainHandler2 = new MyHandler2();

    /**
     * 方式2：匿名内部类
     * 在主线程中 通过匿名内部类 创建Handler类对象
     */
    private Handler mHandler3 = new  Handler(){
        // 通过复写handlerMessage()从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "mHandler3, handleMessage: " + Utils.getPids());
            Log.d(TAG, "mHandler3, handleMessage: msg = " + msg);
        }
    };

    private HandlerThread mHandlerThread;
    private Handler workHandler;

    private TextView testTv;
    private Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        testTv = findViewById(R.id.test_tv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        Log.d(TAG, "onCreate: " + Utils.getPids());

        //方式1、使用sendMessage
        Message msg = Message.obtain(); // 实例化消息对象
        msg.what = 1; // 消息标识
        msg.obj = "AA"; // 消息内容存放
        mainHandler.sendMessage(msg);//注意一个消息不能同时被多个Handler发送！

        //方式2、使用Handler.post（）
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "mHandler.post, run: ---------");
            }
        });

        Message msg3 = Message.obtain();
        msg3.what = 3;
        msg3.obj = "CC";
        mHandler3.sendMessage(msg3);
        //以上三个消息并不能验证在非主线程通知UI线程刷新UI，因为这几个消息都是在主线程中发出的！！！

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Log.d(TAG, "Thread run: " + Utils.getPids());
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = "子线程1刷新UI";
                        mainHandler2.sendMessage(msg);
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = "子线程2刷新UI";
                        mainHandler2.sendMessage(msg);
                    }
                }.start();
            }
        });

        mHandlerThread = new HandlerThread("handlerThread");
        mHandlerThread.start();
        /**
         * 创建工作线程Handler & 复写handleMessage（）
         * 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
         * 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
         */
        workHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "workHandler, handleMessage " + Utils.getPids());
                switch (msg.what) {
                    case 1:
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //以下写法会报错，因为在非UI线程操作UI
                        // android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views
                        //testTv.setText((String)msg.obj);
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                testTv.setText("我爱学习");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "我爱学习";
                workHandler.sendMessage(msg);
            }
        });
    }
}