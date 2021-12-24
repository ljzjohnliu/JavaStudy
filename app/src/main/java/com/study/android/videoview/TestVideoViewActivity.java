package com.study.android.videoview;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.communicate.ProcessComActivity;
import com.study.android.customview.TestCustomViewActivity;
import com.study.android.handler.HandlerActivity2;
import com.study.android.handler.SyncBarrierActivity;
import com.study.android.receiver.TestBroadcastActivity;
import com.study.android.service.TestServiceActivity;
import com.study.android.testfrag.AttachActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestVideoViewActivity extends AppCompatActivity {
    private static final String TAG = "TestVideoView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: thread id = " + Thread.currentThread().getId());
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