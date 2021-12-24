package com.study.android.event;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

public class TestEventActivity extends BaseSimpleActivity {
    private static final String TAG = "TestEventActivity";

    Button button1, button2;
    ViewGroup myLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        myLayout = (LinearLayout) findViewById(R.id.my_layout);

        // 1.为ViewGroup布局设置监听事件
        myLayout.setOnClickListener(v -> Log.d(TAG, "点击了ViewGroup"));

        // 2. 为按钮1设置监听事件
        button1.setOnClickListener(v -> Log.d(TAG, "点击了button1"));

        // 1. 注册Touch事件监听setOnTouchListener 且 在onTouch()返回false
        button2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: 执行了onTouch(), 动作是:" + event.getAction());
                return true;
            }
        });
        // 2. 注册点击事件OnClickListener()
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 执行了onClick()");
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ev.getAction = " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onUserInteraction() {
        Log.d(TAG, "onUserInteraction: ----------");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onUserInteraction: ----------event = " + event);
        return super.onTouchEvent(event);
    }
}