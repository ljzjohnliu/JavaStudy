package com.study.java.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.study.java.R;
import com.study.java.utils.Utils;

public class HandlerActivity extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    //自定义Handler子类（继承Handler类） & 复写handleMessage（）方法
    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "MyHandler, handleMessage: " + Utils.getPids());
            Log.d(TAG, "MyHandler, handleMessage: msg = " + msg);
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

    /**
     * 方式2：匿名内部类
     * 在主线程中 通过匿名内部类 创建Handler类对象
     */
    private Handler mHandler2 = new Handler(){
        // 通过复写handlerMessage()从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "mHandler2, handleMessage: " + Utils.getPids());
            Log.d(TAG, "mHandler2, handleMessage: msg = " + msg);
        }
    };

    private TextView testTv;
    private Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        testTv = findViewById(R.id.test_tv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        Log.d(TAG, "onCreate: " + Utils.getPids());

        //btn1和btn2发送消息并不能验证在非主线程通知UI线程刷新UI，因为这几个消息都是在主线程中发出的！！！
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方式1、使用sendMessage
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "来自Button1的刷新UI操作";
                mainHandler.sendMessage(msg);//注意一个消息不能同时被多个Handler发送！
            }
        });

        btn2.setOnClickListener(v -> {
            //方式2、使用Handler.post（）
            mHandler2.post(() -> {
                Log.d(TAG, "mHandler.post, run: ---------");
                testTv.setText("来自PostRunnable的刷新");
            });
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Log.d(TAG, "Thread1 run: " + Utils.getPids());
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = "子线程1刷新UI";
                        mainHandler.sendMessage(msg);
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
                        Log.d(TAG, "Thread2 run: " + Utils.getPids());
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = "子线程2刷新UI";
                        mainHandler.sendMessage(msg);
                    }
                }.start();
            }
        });

//        Thread lShowToastThread = new Thread() {
//            @Override
//            public void run() {
//                Log.d(TAG, "lShowToastThread, run " + Utils.getPids());
//                Looper.prepare();
//                String toast = " program has crashed";
//                Toast.makeText(HandlerActivity.this, toast, Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        };
//        lShowToastThread.start();
    }
}