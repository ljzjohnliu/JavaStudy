package com.study.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyLocalReceiver extends BroadcastReceiver {
    private final String TAG = "MyLocalReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "MyLocalReceiver, onReceive: start intent = " + intent);
        Toast.makeText(context, "本地广播处理中...", Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "MyLocalReceiver, onReceive: end intent = " + intent);
        Toast.makeText(context, "本地广播处理结束", Toast.LENGTH_LONG).show();
    }
}
