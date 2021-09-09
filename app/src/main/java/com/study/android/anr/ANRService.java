package com.study.android.anr;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

public class ANRService extends Service {
    private final String TAG = "ANRService";
    public static final String P_DOWNLOAD_URL = "download_url";

    public static void startUpdate(final Context context, final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        startDownloadPkg(context, url);
    }

    private static final void startDownloadPkg(final Context context, final String url) {
        Intent intent = new Intent(context, ANRService.class);
        intent.putExtra(ANRService.P_DOWNLOAD_URL, url);
        context.startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_REDELIVER_INTENT;
        }
        String url = intent.getStringExtra(P_DOWNLOAD_URL);

        /**
         * 这里验证下来10s以上就会ANR，小于10s就没问题，
         * 这个跟网上查到的说SERVICE_TIMEOUT = 20*1000; // 前台
         * 不一致，有待考究
         */
        try {
            Log.d("TAG", "Simulate network requests start");
            Thread.sleep(9999);
            Log.d("TAG", "Simulate network requests end");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("TAG", "Simulate network requests e = " + e);
        }

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

