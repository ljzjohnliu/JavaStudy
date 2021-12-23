package com.study.android.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.utils.Utils;

public class HandlerActivity3 extends AppCompatActivity {

    private static final String TAG = "HandlerActivity";

    //自定义Handler子类（继承Handler类） & 复写handleMessage（）方法
    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "MyHandler, handleMessage: msg = " + msg);
//            switch (msg.what) {
//                case 2:
//                    testTv.setText((String)msg.obj);
//                default:
//                    break;
//            }
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

    private TextView testTv;
    private Button btn1, btn2, btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler3);
        testTv = findViewById(R.id.test_tv);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        Log.d(TAG, "onCreate: " + Utils.getPids());
        Message msg1 = Message.obtain();
        msg1.obj = "AAA delay 3s的消息";
        mainHandler.sendMessageDelayed(msg1, 3000);
        Message msg2 = Message.obtain();
        msg2.obj = "BBB delay 3s的消息";
        mainHandler.sendMessageDelayed(msg2, 3000);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Log.d(TAG, "onCreate: InterruptedException e = " + e);
        }
        Message msg3 = Message.obtain();
        msg3.obj = "CCC delay 3s的消息";
        mainHandler.sendMessageDelayed(msg3, 3000);
        Message msg4 = Message.obtain();
        msg4.obj = "DDD delay 3s的消息";
        mainHandler.sendMessageDelayed(msg4, 2999);

        //btn1和btn2发送消息并不能验证在非主线程通知UI线程刷新UI，因为这几个消息都是在主线程中发出的！！！
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方式1、使用sendMessage
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "来自Button1的delay 3s的消息";
                mainHandler.sendMessageDelayed(msg, 3000);
            }
        });

        btn2.setOnClickListener(v -> {
            //方式2、使用Handler.post（）
            Message msg = Message.obtain();
            msg.what = 2;
            msg.obj = "来自Button2的delay 5s的消息";
            mainHandler.sendMessageDelayed(msg, 5000);
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = "来自Button3的delay 1s的消息";
                mainHandler.sendMessageDelayed(msg, 1000);
            }
        });
    }
}