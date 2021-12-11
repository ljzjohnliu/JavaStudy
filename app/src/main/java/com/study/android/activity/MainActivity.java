package com.study.android.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.communicate.ProcessComActivity;
import com.study.android.lifecycle.ActivityA;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.test_activity_life, R.id.test_process_comm})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.test_activity_life:
                // 此方法不能实现跨应用调用
//                intent.setClass(this, ActivityA.class);
                intent.setComponent(new ComponentName("com.study.android", "com.study.android.lifecycle.ActivityA"));
                break;
            case R.id.test_process_comm:
                // 此方法不能实现跨应用调用
                intent.setClass(this, ProcessComActivity.class);
                break;
        }
        startActivity(intent);
    }
}