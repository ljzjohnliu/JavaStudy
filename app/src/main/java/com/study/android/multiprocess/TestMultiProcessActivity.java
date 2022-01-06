package com.study.android.multiprocess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestMultiProcessActivity extends BaseSimpleActivity {
    private static final String TAG = "TestMultiProcessAc";

    private boolean isBind = false;
    private NewProcessService service;

    @OnClick({R.id.start_new_process_activity, R.id.start_new_process_service, R.id.start_new_process_receiver})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.start_new_process_activity:
                intent.setClass(this, NewProcessActivity.class);
                startActivity(intent);
                break;
            case R.id.start_new_process_service:
                intent.setClass(this, NewProcessService.class);
                startService(intent);
                break;
            case R.id.start_new_process_receiver:
                Log.d(TAG, "onClick: ");
                intent.setAction("com.ljz.action.TEST");
                sendBroadcast(intent);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_multi_process);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: this  = " + this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}