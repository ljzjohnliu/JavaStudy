package com.study.android.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerActivity4 extends BaseSimpleActivity {

    private static final String TAG = "HandlerActivity";
    @BindView(R.id.test_tv)
    TextView testTv;

    private long startTime;

    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "mainHandler, handleMessage: msg = " + msg.what + ", cost = " + (System.currentTimeMillis() - startTime));
            switch (msg.what) {
                case 1:
                    testTv.setText("1111111");
                    simulationCostTime();
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case 2:
                    testTv.setText("2222222");
                default:
                    break;
            }
        }
    };

    int arr[] = new int[]{1, 7, 4, 3, 6, 3, 2, 5, 8};
    public static void bubbleSort(int[] arr) {
        int temp;//定义一个临时变量
        for (int i = 0; i < arr.length - 1; i++) {//冒泡趟数
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public void simulationCostTime() {
        for (int i = 0; i < 30000000; i++) {
            bubbleSort(arr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler2);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: ----------send---1111-----");
        Message message1 = Message.obtain();
        message1.what = 1;
        mainHandler.sendMessageDelayed(message1, 1000);
        Log.d(TAG, "onCreate: ----------send---2222-----");
        startTime = System.currentTimeMillis();
        Message message2 = Message.obtain();
        message2.what = 2;
        mainHandler.sendMessageDelayed(message2, 2000);

//        simulationCostTime();
        Log.d(TAG, "onCreate: bubbleSort , cost = " + (System.currentTimeMillis() - startTime));
    }
}