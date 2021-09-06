package com.study.android.handler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.activity.MainActivity;

public class LeakHandlerActivity extends AppCompatActivity {

    private static final String TAG = "LeakHandler";

    /**
     * 明确一点: java的内部类会默认持有外部类的对象引用。在这段代码的表现就是handler会持有MainActivity这个对象的引用。
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage: msg = " + msg);
        }
    };

    private Thread leakThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_leak);
        leakThread = new Thread(new LeakRunnable(handler));
        leakThread.start();
        Button button = findViewById(R.id.btn1);
        button.setOnClickListener(v -> {
            startActivity(new Intent(LeakHandlerActivity.this, MainActivity.class));
            finish();
        });
    }
}