package com.study.android.receiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestBroadcastActivity extends BaseSimpleActivity {
    private static final String TAG = "TestBroadcast";
    private static final String LOCAL_BROADCAST_ACTION = "com.ljz.local.action";

    LocalBroadcastManager localBroadcastManager;
    MyLocalReceiver myLocalReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_receiver);
        ButterKnife.bind(this);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myLocalReceiver = new MyLocalReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.register_local, R.id.unregister_local, R.id.local_send, R.id.local_send_sync})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_local:
                registerLocalReceiver();
                break;
            case R.id.unregister_local:
                unregisterLocalReceiver();
                break;
            case R.id.local_send:
                sendLocalBroadcast();
                break;
            case R.id.local_send_sync:
                sendLocalBroadcastSync();
                break;
        }
    }

    private void registerLocalReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LOCAL_BROADCAST_ACTION);
        localBroadcastManager.registerReceiver(myLocalReceiver, intentFilter);
    }

    private void unregisterLocalReceiver() {
        localBroadcastManager.unregisterReceiver(myLocalReceiver);
    }

    private void sendLocalBroadcast() {
        Log.d(TAG, "sendLocalBroadcast: ------- start ------");
        localBroadcastManager.sendBroadcast(new Intent(LOCAL_BROADCAST_ACTION));
        Log.d(TAG, "sendLocalBroadcast: ------- end ------");
    }

    private void sendLocalBroadcastSync() {
        Log.d(TAG, "sendLocalBroadcastSync: ******* start ******");
        localBroadcastManager.sendBroadcastSync(new Intent(LOCAL_BROADCAST_ACTION));
        Log.d(TAG, "sendLocalBroadcastSync: ******* end ******");
    }
}