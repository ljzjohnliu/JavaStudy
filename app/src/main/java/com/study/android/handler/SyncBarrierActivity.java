package com.study.android.handler;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyncBarrierActivity extends AppCompatActivity {

    private static final String TAG = "SyncBarrier";
    @BindView(R.id.test_tv)
    TextView testTv;

    @OnClick({R.id.btn_sync_1, R.id.btn_sync_2, R.id.btn_sync_3, R.id.btn_async_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sync_1:
//                TextView textView = new TextView(this);
//                for (int i = 0; i < 10; i++) {
//                    Message msg = Message.obtain();
//                    msg.obj = i + "的消息";
//                    mainHandler.sendMessage(msg);
//                }

                for (int i = 0; i < 3; i++) {
                    Message msg = Message.obtain();
                    msg.obj = i + "的异步消息";
                    msg.setAsynchronous(true);
                    mainHandler2.sendMessage(msg);
                    testTv.setText(i + "我要刷新这个View");
                }
                break;
            case R.id.btn_sync_2:
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)testTv.getLayoutParams();
                layoutParams.topMargin = 40;
                testTv.setLayoutParams(layoutParams);
                testTv.setText("我要刷新这个View");
                break;
            case R.id.btn_sync_3:
                break;
            case R.id.btn_async_1:
                break;
        }
    }

    //自定义Handler子类（继承Handler类） & 复写handleMessage（）方法
    class MyHandler extends Handler {

        public MyHandler() {
            super();
        }

        public MyHandler(Looper looper, Handler.Callback callback) {
            super(looper, callback);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "MyHandler, handleMessage: msg.obj = " + msg.obj + ", isAsynchronous = " + msg.isAsynchronous());
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
     * 创建Handler的时候构造器有Callback的话，优先callback处理message，这个Callback中的handleMessage是有返回值的
     * 返回false的话，message会继续交给Handler的handleMessage处理，返回true就到此为止！
     */
    private Handler mainHandler2 = new MyHandler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.d(TAG, "Callback, handleMessage: msg = " + msg);
            return false;
        }
    });

    private Handler asyncHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_barrier);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + Utils.getPids());

        /**
         * 异步Handler发送的消息都是异步消息
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            asyncHandler = Handler.createAsync(Looper.getMainLooper());
            Message msg = Message.obtain();
            msg.obj = "****异步消息****";
            asyncHandler.sendMessage(msg);
            asyncHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: ------asyncHandler-----");
                }
            });
        }
    }
}