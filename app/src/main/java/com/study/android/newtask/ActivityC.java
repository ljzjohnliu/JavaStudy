package com.study.android.newtask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.study.android.R;
import com.study.android.activity.MainActivity;
import com.study.android.base.BaseSimpleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityC extends BaseSimpleActivity {
    private static final String TAG = "CCCC";

    @OnClick({R.id.start_activity_main})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.start_activity_main:
                intent.setClass(this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_c);
        ButterKnife.bind(this);
        Log.d(TAG, "------onCreate: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "------onNewIntent: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: ");
    }
}