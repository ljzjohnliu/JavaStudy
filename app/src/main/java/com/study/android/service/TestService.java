package com.study.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.study.android.utils.ProcessUtil;
import com.study.android.utils.Utils;

import java.util.Random;

public class TestService extends Service {
    private static final String TAG = "TestService";

    private final Random generator = new Random();

    private MyBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {

        public TestService getService() {
            return TestService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ----");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ----from = " + intent.getStringExtra("from"));
        return false;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate:  getPids = " + Utils.getPids() + ", 进程：" + ProcessUtil.getCurrentProcessName());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ----");
//        return START_REDELIVER_INTENT;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ----");
    }

    public int getRandomNumber() {
        return generator.nextInt();
    }
}
