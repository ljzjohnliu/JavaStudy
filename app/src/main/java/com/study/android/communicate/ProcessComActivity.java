package com.study.android.communicate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.ProcessUtil;
import com.study.android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProcessComActivity extends BaseSimpleActivity {
    private static final String TAG = "ProcessComActivity";
    public static final String KEY_FROM = "KEY_FROM";
    public static final int RESULT_FROM_REMOTE = 1001;
    @BindView(R.id.title)
    TextView titleTv;

    private IBookAidlInterface iBookAidlInterface = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iBookAidlInterface = IBookAidlInterface.Stub.asInterface(service);
            try {
                String bookName = iBookAidlInterface.getTitle();
                Log.d(TAG, "getTitle: bookName = " + bookName);
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.d(TAG, "onServiceConnected: e = " + e);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * bindService启动流程
     * context.bindService()  -> onCreate()  -> onBind()  -> Service running  -> onUnbind()  -> onDestroy()  -> Service stop
     */
    private void bindService() {
        Intent intent = new Intent(ProcessComActivity.this, RemoteService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ActivityResultLauncher myActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_FROM_REMOTE) {
            String name = result.getData().getStringExtra(KEY_FROM);
            titleTv.setText("来自Remote的value:" + name);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate:  getPids = " + Utils.getPids() + ", 进程：" + ProcessUtil.getCurrentProcessName());
        titleTv.setText("当前进程：" + ProcessUtil.getCurrentProcessName());
        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @OnClick({R.id.remote_activity, R.id.get_book})
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * 匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加容易维护。
             * 代码中需要给按钮设置监听器对象，使用匿名内部类能够在实现父类或者接口中的方法情况下同时产生一个相应的对象，
             *
             * 匿名内部类也是不能有访问修饰符和static修饰符的。
             *
             * 匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，大部分匿名内部类用于接口回调。
             * 匿名内部类在编译的时候由系统自动起名为Outter$1.class。
             * 一般来说，匿名内部类用于继承其他类或是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是重写。
             */
            case R.id.remote_activity:
                //D TAG     : onClick: title!!! this = com.study.android.activity.ProcessComActivity$1@58f606c
                Log.d(TAG, "remote_activity, onClick: this = " + this);
                Intent mainIntent = new Intent(ProcessComActivity.this, RemoteActivity.class);
                mainIntent.putExtra(KEY_FROM, "main process");
                myActivityLauncher.launch(mainIntent);
                break;
            case R.id.get_book:
                try {
                    iBookAidlInterface.setTitle("我是一个title");
                    Log.d(TAG, "get_book, onClick: modify bookName = " + iBookAidlInterface.getTitle());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.d(TAG, "get_book, onClick: e = " + e);
                }
                break;
        }
    }
}