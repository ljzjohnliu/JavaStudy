package com.study.android.communicate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;

import com.study.android.utils.ProcessUtil;
import com.study.android.utils.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteServiceB extends Service {
    private static final String TAG = "RemoteServiceB";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ----");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ----from = " + intent.getStringExtra("from"));
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind: ----intent = " + intent);
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate:  getPids = " + Utils.getPids() + ", 进程：" + ProcessUtil.getCurrentProcessName());
        super.onCreate();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ----");
        isActive = true;
        createSocket();
//        return START_REDELIVER_INTENT;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isActive = false;
        Log.d(TAG, "onDestroy: ----");
    }

    boolean isActive;

    private void createSocket() {
        while (isActive) { //表示服务生存着
            try {
                ServerSocket serverSocket = new ServerSocket(8688);
                final Socket client = serverSocket.accept();  //不断获取客户端连接
                Log.d(TAG, "createSocket: 服务端已获取客户端连接");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            dealWithMessageFromClient(client);  //处理客户端的消息 就是开启一个线程循环获取out 和 in  流 进行通信
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dealWithMessageFromClient(Socket socket) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), "UTF-8"));
        printWriter.println("成功连接服务器" + "（服务器发送）");
        Log.d(TAG, "dealWithMessageFromClient: 成功连接服务器");
        String receiveMsg;
        String sendMsg = "我已接收：服务器发送";
        while (true) {                                   //循环接收、读取 Client 端发送过来的信息
            if ((receiveMsg = in.readLine()) != null) {
                Log.d(TAG, "dealWithMessageFromClient: receiveMsg = " + receiveMsg);
                if (receiveMsg.equals("0")) {
                    Log.d(TAG, "dealWithMessageFromClient: 客户端请求断开连接!");
                    printWriter.println("服务端断开连接" + "（服务器发送）");
                    in.close();
                    socket.close();                         //接受 Client 端的断开连接请求，并关闭 Socket 连接
                    break;
                } else {
                    sendMsg = "我已接收：" + receiveMsg + "（服务器发送）";
                    printWriter.println(sendMsg);           //向 Client 端反馈、发送信息
                }
            }
        }
    }
}
