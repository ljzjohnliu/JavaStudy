package com.study.android.multiprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NewProcessReceiver extends BroadcastReceiver {
    private final String TAG = "NewProcessReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "NewProcessReceiver, onReceive: start intent = " + intent);
        Toast.makeText(context, "本地广播处理中...", Toast.LENGTH_LONG).show();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "NewProcessReceiver, onReceive: end intent = " + intent);
        Toast.makeText(context, "本地广播处理结束", Toast.LENGTH_LONG).show();
    }
}
