package com.study.android.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerActivity extends BaseSimpleActivity {

    private static final String TAG = "HandlerActivity";
    @BindView(R.id.test_tv)
    TextView testTv;

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onClick(View view) {
        //btn1和btn2发送消息并不能验证在非主线程通知UI线程刷新UI，因为这几个消息都是在主线程中发出的！！！
        switch (view.getId()) {
            case R.id.btn1:
                //方式1、使用sendMessage
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = "来自Button1的刷新UI操作";
                mainHandler.sendMessage(msg);//注意一个消息不能同时被多个Handler发送！
                break;
            case R.id.btn2:
                //方式2、使用Handler.post（）
                mHandler2.post(() -> {
                    Log.d(TAG, "mHandler.post, run: ---------" + Utils.getPids());
                    testTv.setText("来自PostRunnable的刷新");
                });
                break;
            case R.id.btn3:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //这里打印的是线程2的信息，因为它是运行在线程2中的
                        Log.d(TAG, "Thread1 run: " + Utils.getPids());
                        Message msg = Message.obtain();
                        msg.what = 2;
                        msg.obj = "子线程1刷新UI";
                        mainHandler.sendMessage(msg);
                    }
                }.start();
                break;
            case R.id.btn4:
                new Thread() {
                    @Override
                    public void run() {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //这里打印的是主线程的信息，因为它是运行在mainHandler所在的线程的
                                Log.d(TAG, "Thread2 mHandler.post, run: ---------" + Utils.getPids());
                                testTv.setText("来自PostRunnable的刷新");
                            }
                        });
                    }
                }.start();
                break;
        }
    }

    //自定义Handler子类（继承Handler类） & 复写handleMessage（）方法
    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "MyHandler, handleMessage: " + Utils.getPids());
            Log.d(TAG, "MyHandler, handleMessage: msg = " + msg);
            switch (msg.what) {
                case 2:
                    testTv.setText((String) msg.obj);
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
     * 这里创建的其实是绑定了主线程Looper和MessageQueue的Handler
     */
    private Handler mHandler2 = new Handler() {
        // 通过复写handlerMessage()从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "mHandler2, handleMessage: " + Utils.getPids());
            Log.d(TAG, "mHandler2, handleMessage: msg = " + msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + Utils.getPids());

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

//        for(int i = 0; i < 10; i++) {
//            Message msg = Message.obtain();
//            msg.what = 2;
//            msg.obj = "刷新UI" + i;
//            mainHandler.sendMessageDelayed(msg);
//        }

        Message msg1 = Message.obtain();
        msg1.what = 2;
        msg1.obj = "刷新UI";
        mainHandler.sendMessageDelayed(msg1, 0);
        /* 更多IdleHandler知识请看链接
        https://blog.csdn.net/jdsjlzx/article/details/110532500
        IdleHandler 说白了，就是 Handler 机制提供的一种，可以在 Looper 事件循环的过程中，当出现空闲的时候，允许我们执行任务的一种机制。
        * MessageQueue 是一个基于消息触发时间的优先级队列，所以队列出现空闲存在两种场景。
        * 1、MessageQueue 为空，没有消息；
        * 2、MessageQueue 中最近需要处理的消息，是一个延迟消息（when>currentTime），需要滞后执行；
        * 这两个场景，都会尝试执行 IdleHandler。 */
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.d(TAG, "queueIdle: -------------");
                return false;
            }
        });
    }
}