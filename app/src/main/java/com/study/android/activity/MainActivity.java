package com.study.android.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.communicate.ProcessComActivity;
import com.study.android.handler.HandlerActivity3;
import com.study.android.service.TestServiceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.test_activity_life)
    Button testActivityLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: thread id = " + Thread.currentThread().getId());
        new Thread() {
            @Override
            public void run() {
//                Looper.prepare();
//                Handler handler = new Handler();
//                Looper.loop();
                Log.d(TAG, "run: thread id = " + Thread.currentThread().getId());
//                testActivityLife.setText("hello");
                testActivityLife.invalidate();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.test_activity_life, R.id.test_service_life, R.id.test_process_comm, R.id.test_handler})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.test_activity_life:
                // 此方法不能实现跨应用调用
//                intent.setClass(this, ActivityA.class);
                intent.setComponent(new ComponentName("com.study.android", "com.study.android.lifecycle.ActivityA"));
                break;
            case R.id.test_service_life:
                intent.setClass(this, TestServiceActivity.class);
                break;
            case R.id.test_process_comm:
                intent.setClass(this, ProcessComActivity.class);
                break;
            case R.id.test_handler:
                intent.setClass(this, HandlerActivity3.class);
                break;
        }
        startActivity(intent);
    }
}