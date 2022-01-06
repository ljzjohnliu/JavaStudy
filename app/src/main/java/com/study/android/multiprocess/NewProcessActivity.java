package com.study.android.multiprocess;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.lifecycle.ActivityA;
import com.study.android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewProcessActivity extends BaseSimpleActivity {
    private static final String TAG = "NewProcessActivity";

    @BindView(R.id.title)
    TextView titleTv;

    @OnClick({R.id.return_activity_a})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.return_activity_a:
//                intent.setClass(this, ActivityA.class);
                finish();
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_process);
        ButterKnife.bind(this);
        Log.d(TAG, "------onCreate: " + Utils.getPids());
        titleTv.setText("我是新进程的Activity\n" + Utils.getPids());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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