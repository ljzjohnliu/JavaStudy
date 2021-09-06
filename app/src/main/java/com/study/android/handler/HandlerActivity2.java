package com.study.android.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.utils.Utils;

public class HandlerActivity2 extends AppCompatActivity {

    private static final String TAG = "HandlerActivity2";

    private Handler mainHandler = new Handler();
    private HandlerThread mHandlerThread;
    private Handler workHandler;

    private MyThread workThread;
    private Handler workHandler2;

    private TextView testTv;
    private Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler2);
        testTv = findViewById(R.id.test_tv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        Log.d(TAG, "onCreate: " + Utils.getPids());

        /**
         * HandlerThread对象start后可以获得其Looper对象，并且使用这个Looper对象实例Handler，之后Handler就可以运行在其他线程中了
         */
        mHandlerThread = new HandlerThread("handlerThread", 999) {
            @Override
            protected void onLooperPrepared() {
                Log.d(TAG, "onLooperPrepared: **********" +  Utils.getPids());
            }
        };
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
                        //以下写法会报错，因为在非UI线程操作UI android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views
                        //testTv.setText((String)msg.obj);
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        mainHandler.post(() -> testTv.setText("我爱学习"));
                        break;
                    default:
                        break;
                }
            }
        };

        btn1.setOnClickListener(v -> {
            Message msg1 = Message.obtain();
            msg1.what = 1;
            msg1.obj = "HandlerThread刷新UI:我爱学习";
            workHandler.sendMessage(msg1);
        });

        workThread = new MyThread("workThread");
        workThread.start();

        //困惑点：workHandler2还是在主线程中的!!!!
        Log.d(TAG, "onCreate: Looper.getMainLooper() = " + Looper.getMainLooper());
        Log.d(TAG, "onCreate: workThread.getLooper() = " + workThread.getLooper());
        workHandler2 = new Handler(workThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(TAG, "workHandler2, handleMessage " + Utils.getPids());
                switch (msg.what) {
                    case 1:
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        testTv.setText((String)msg.obj);
                        break;
                    default:
                        break;
                }
            }
        };

        btn2.setOnClickListener(v -> {
            Message msg1 = Message.obtain();
            msg1.what = 1;
            msg1.obj = "普通Thread刷新UI:我要玩游戏";
            workHandler2.sendMessage(msg1);
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg1 = Message.obtain();
                msg1.what = 1;
                msg1.obj = "普通Thread刷新UI2:我要睡觉";
                workThread.mHandler.sendMessage(msg1);
            }
        });
    }
}