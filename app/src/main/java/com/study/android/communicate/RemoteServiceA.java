package com.study.android.communicate;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.study.android.utils.ProcessUtil;
import com.study.android.utils.Utils;

public class RemoteServiceA extends Service {
    private static final String TAG = "RemoteServiceA";

    private class MessageHandler extends Handler {  //创建的接受消息的handler
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    String str = bundle.getString("aaa");
                    Log.d(TAG, "handleMessage: msg.replyTo = " + msg.replyTo + ", str = " + str);
                    Toast.makeText(RemoteServiceA.this, str, Toast.LENGTH_SHORT).show();
                    Messenger replyTo = msg.replyTo; //此处往下是用来回复消息给客户端 ProcessComActivity 的
                    Message replyMsg = Message.obtain(null, 2);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("bbb", "remote 给主进程回复消息啦");
                    replyMsg.setData(bundle1);
                    try {
                        replyTo.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Log.d(TAG, "handleMessage: e = " + e);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

    Messenger messenger = new Messenger(new MessageHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ----");
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ----from = " + intent.getStringExtra("from"));
        return true;
    }

    /**
     * Service中onRebind方法被调用，要符合两个必要条件就行
     * (1)服务中onUnBind方法返回值为true
     * (2)服务对象被解绑后没有被销毁，之后再次被绑定。
     */
    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ----intent = " + intent);
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
}
