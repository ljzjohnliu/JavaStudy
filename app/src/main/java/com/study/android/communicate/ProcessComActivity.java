package com.study.android.communicate;

import static com.study.android.activity.TestEnum.TEST_ITEM2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.ProcessUtil;
import com.study.android.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProcessComActivity extends BaseSimpleActivity {
    private static final String TAG = "ProcessComActivity";
    public static final String KEY_FROM = "KEY_FROM";
    public static final int RESULT_FROM_REMOTE = 1001;
    @BindView(R.id.title)
    TextView titleTv;
    @BindView(R.id.print_result)
    TextView resultTv;

    private IBookAidlInterface iBookAidlInterface = null;
    Messenger messenger;

    /**
     * 如果是远程服务的话，这里onServiceConnected的第二个参数是android.os.BinderProxy！！！
     * java.lang.ClassCastException: android.os.BinderProxy cannot be cast to com.study.android.communicate.RemoteServiceA$MyBinder
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: name = " + name + ", service = " + service);
            Log.d(TAG, "onServiceConnected: getPackageName = " + name.getPackageName() + ", getClassName = " + name.getClassName()
                    + ", getShortClassName = " + name.getShortClassName());
            //跨进程通信方式1、采用AIDL实现
            if ("com.study.android.communicate.RemoteService".equals(name.getClassName())) {
                iBookAidlInterface = IBookAidlInterface.Stub.asInterface(service);
                try {
                    String bookName = iBookAidlInterface.getTitle();
                    Log.d(TAG, "getTitle: bookName = " + bookName);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onServiceConnected: e = " + e);
                }
                //跨进程通信方式2、采用Messenger实现
            } else if ("com.study.android.communicate.RemoteServiceA".equals(name.getClassName())) {
                messenger = new Messenger(service);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ----");
        }
    };

    /*------------------跨进程通信方式2、用到的Messenger以及Handler------------------*/
    Messenger localMessenger = new Messenger(new MessageHandler());

    private class MessageHandler extends Handler {  //创建的接受消息的handler
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: msg = " + msg);
            switch (msg.what) {
                case 2:
                    Bundle bundle = msg.getData();
                    String str = bundle.getString("bbb");
                    Log.d(TAG, "handleMessage: str = " + str);
                    Toast.makeText(ProcessComActivity.this, str, Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    //跨进程通信方式4、采用Socket实现
    private class SocketHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: msg = " + msg);
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "SocketHandler, handleMessage: -------- ");
                    break;
                case 2:
                    String str = (String) msg.obj;
                    Log.d(TAG, "SocketHandler, handleMessage: str = " + str);
                    Toast.makeText(ProcessComActivity.this, str, Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private SocketHandler handler = new SocketHandler();
    private BufferedReader reader;
    private PrintWriter out;

    private void connectSocket() {
        Socket socket = null;
        while (socket == null) {  //失败重连
            try {
                socket = new Socket("localhost", 8688);
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println("我是来自客户端 ProcessComActivity 的数据");
                handler.sendEmptyMessage(1);
                final Socket finalSocket = socket;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            reader = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        while (!ProcessComActivity.this.isFinishing()) {  //循环获取消息  这里必须用 循环 否则 只能获取一条消息 服务端也一样
                            try {
                                String msg = reader.readLine();
                                Log.d(TAG, "run: --- msg = " + msg);
                                if (msg != null) {
                                    handler.sendMessage(handler.obtainMessage(2, msg));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
//                        }
                    }
                }.start();
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }
    }

    /**
     * bindService启动流程
     * context.bindService()  -> onCreate()  -> onBind()  -> Service running  -> onUnbind()  -> onDestroy()  -> Service stop
     */
    private void bindService() {
        Intent intent1 = new Intent(ProcessComActivity.this, RemoteService.class);
        bindService(intent1, serviceConnection, Context.BIND_AUTO_CREATE);

        Intent intent2 = new Intent(this, RemoteServiceA.class);
        intent2.putExtra("from", "ProcessComActivity");
        bindService(intent2, serviceConnection, BIND_AUTO_CREATE);

        startService(new Intent(this, RemoteServiceB.class));
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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

    @OnClick({R.id.remote_activity, R.id.get_book, R.id.modify_book, R.id.messenger,
            R.id.socket})
    public void onClick(View view) {
        switch (view.getId()) {
            //跨进程通信方式1、采用Bundle实现
            case R.id.remote_activity:
                //D TAG     : onClick: title!!! this = com.study.android.activity.ProcessComActivity$1@58f606c
                Log.d(TAG, "remote_activity, onClick: this = " + this);
                Intent mainIntent = new Intent(ProcessComActivity.this, RemoteActivity.class);
                mainIntent.putExtra(KEY_FROM, "main process");
                mainIntent.putExtra("enum", TEST_ITEM2);
                myActivityLauncher.launch(mainIntent);
                break;
            case R.id.get_book:
                try {
                    resultTv.setText("获取到远程进程的书名：" + iBookAidlInterface.getTitle());
                    Log.d(TAG, "get_book, onClick: get bookName = " + iBookAidlInterface.getTitle());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.d(TAG, "get_book, onClick: e = " + e);
                }
                break;
            case R.id.modify_book:
                try {
                    iBookAidlInterface.setTitle("修改后书名《Android开发艺术》");
                    Log.d(TAG, "get_book, onClick: modify bookName = " + iBookAidlInterface.getTitle());
                    resultTv.setText("修改书名成功，再查询一下看看吧");
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Log.d(TAG, "get_book, onClick: e = " + e);
                    resultTv.setText("修改书名失败 " + e);
                }
                break;
            case R.id.messenger:
                Message msg = Message.obtain(null, 1);
                Bundle bundle = new Bundle();
                bundle.putString("aaa", "主进程给 remote 进程发消息啦");
                msg.setData(bundle);
                msg.replyTo = localMessenger; //这行代码用于客户端A接收服务端请求 设置的消息接收者
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.socket:
                connectSocket();
                break;
        }
    }
}