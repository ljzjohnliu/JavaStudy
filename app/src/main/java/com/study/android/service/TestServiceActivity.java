package com.study.android.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.lifecycle.ActivityC;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestServiceActivity extends AppCompatActivity {
    private static final String TAG = "TestSerActivity";

    private boolean isBind = false;
    private TestService service;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            isBind = true;
            TestService.MyBinder binder = (TestService.MyBinder) iBinder;
            service = binder.getService();
            Log.d(TAG, "onServiceConnected: service = " + service);
            int randomNum = service.getRandomNumber();
            Log.d(TAG, "onServiceConnected: randomNum = " + randomNum);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBind = false;
            Log.d(TAG, "onServiceDisconnected: ----");
        }
    };

    @OnClick({R.id.start_service, R.id.stop_service, R.id.bind_service, R.id.unbind_service})
    public void onClick(View view) {
        Intent intent = new Intent(this, TestService.class);
        switch (view.getId()) {
            case R.id.start_service:
                startService(intent);
                break;
            case R.id.stop_service:
                stopService(intent);
                break;
            case R.id.bind_service:
                intent.putExtra("from", "TestSerActivity");
                bindService(intent, conn, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Log.d(TAG, "onClick: unbind_service isBind = " + isBind);
                if (isBind) {
                    unbindService(conn);
                } else {
                    Toast.makeText(this, "当前未绑定任何Service!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        ButterKnife.bind(this);
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