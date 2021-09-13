package com.study.android.communicate;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.study.android.utils.ProcessUtil;
import com.study.android.utils.Utils;

public class RemoteService extends Service {
    private final String TAG = "RemoteService";
    public static final String P_DOWNLOAD_URL = "download_url";

    private Book mBook = new Book();

    private Binder binder = new IBookAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getTitle() throws RemoteException {
            return mBook.title;
        }

        @Override
        public void setTitle(String title) throws RemoteException {
            if (mBook != null) {
                mBook.title = title;
            }
        }

        @Override
        public Book getBook() throws RemoteException {
            if (mBook == null) {
                mBook = new Book();
            }
            return mBook;
        }

        @Override
        public void setBook(Book book) throws RemoteException {
            mBook = book;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
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
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
