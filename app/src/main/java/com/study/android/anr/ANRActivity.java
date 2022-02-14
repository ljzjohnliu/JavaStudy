package com.study.android.anr;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.communicate.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ANRActivity extends BaseSimpleActivity {
    private final String TAG = "ANRActivity";

    @BindView(R.id.sleep_tv)
    TextView sleepTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr);
        ButterKnife.bind(this);
        if(getIntent() != null) {
            Book book = getIntent().getParcelableExtra("book");
            Log.d(TAG, "onCreate: book = " + book);
            Log.d(TAG, "onCreate: id = " + book.id + ", author = " + book.author + " title = " + book.title);
        }
    }

    @OnClick({R.id.sleep_tv, R.id.other_tv, R.id.start_anr_service, R.id.start_my_service, R.id.start_my_service2, R.id.send_broadcast})
    public void onClick(View view) {
        switch (view.getId()) {
            /**
             * 1.对Thread.sleep(long duration)的认知。
             *   由于CPU分配的每个线程的时间片极为短暂(一般为几十毫秒)，所以，CPU通过不停地切换线程执行，这样就给程序员一种错觉，以为多个线程是在同时执行。
             *   sleep就是正在执行的线程主动让出CPU，CPU去执行其他线程，在sleep指定的时间过后，CPU才会回到这个线程上继续往下执行.
             * 2.对耗时操作和Thread.sleep(long duration)的认知。
             *   通常情况下，某些同学对耗时操作的理解就是执行了一定耗时逻辑（比如，while循环或者进行了网络请求之类操作）。
             *   认为Thread.sleep(long duration)是让出了当前线程的cpu执行权，相当于当前线程的休眠，所以不属于耗时。
             *   这样理解比较狭隘，所谓耗时，即当前线程停滞不前，不在执行后面的逻辑，因此两者都能满足，
             *   只不过一个耗时操作把时间耗在了执行耗时逻辑，一个耗时把时间耗在了休眠上。
             *   正是基于此，所以大家才会经常使用Thread.sleep(long duration)来模拟耗时操作。
             * 3.以前我的理解就是 “在主线程做了耗时操作”就会引起ANR，现在我觉得我是错误的，为什么呢？
             *   因为ANR的意思是应用没有响应，但是耗时操作实际上 并不一定会导致没有响应。
             *   我对没有响应的理解是:
             *   有人（事件或操作）发出了一个请求，但是主线程没有对这个人进行反馈（可能是没时间、可能是不想理、可能是手被绑住了没有办法理你），这个叫没有响应
             *
             * 以下代码在 点击事件 中 sleep 了 30秒,然后更新testText，会出现 ANR 吗？
             * 答案是：可能会，也可能不会
             * (1)不会出现ANR的情况：
             *    如果点击了”测试按钮“，之后的30s之内，我们没有进行手动触摸操作（即没有进行任何操作），则不会发生ANR，
             *    这是因为这段代码里面的sleep休眠了线程，代码里面的更新操作根本没有在 sleep的时候被触发（处于休眠状态），
             *    也就没有了发送请求的前提条件，所以并没有发生ANR。
             *
             * (2)会出现ANR的情况：
             *    如果用户手动进行了触摸操作（比如点击屏幕或者按返回键），这里可以点击另一个控件触发其点击事件，5s内无响应，
             *    相当于有一个请求的事件了，而主线程又被休眠了，超过了规定的时间就会触发ANR提示。
             */
            case R.id.sleep_tv:
                try {
                    Log.d(TAG, "sleepTv, onClick: sleep start");
                    Thread.sleep(30000);
                    Log.d(TAG, "sleepTv, onClick: sleep end-");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d(TAG, "sleepTv, onClick: sleep e = " + e);
                }
                sleepTv.setText("sleepTv 30s!!");
                break;
            case R.id.other_tv:
                Log.d(TAG, "otherTv, is onClicked!!!");
                break;
            case R.id.start_anr_service:
                Log.d(TAG, "start ANR ServiceTv, is onClicked!!!");
                ANRService.startUpdate(ANRActivity.this, "http://moni-download.com");
                break;
            case R.id.start_my_service:
                Log.d(TAG, "start My ServiceTv, is onClicked!!!");
                Intent myIntent = new Intent(this, MyService.class);
                myIntent.putExtra("task", "播放音乐");
                startService(myIntent);
                myIntent.putExtra("task", "播放视频");
                startService(myIntent);
                myIntent.putExtra("task", "播放图片");
                startService(myIntent);
                break;
            case R.id.start_my_service2:
                Log.d(TAG, "start My ServiceTv 22 , is onClicked!!!");
                Intent myIntent2 = new Intent(this, MyService.class);
                myIntent2.putExtra("task", "再次执行MyService");
                startService(myIntent2);
                break;
            /**
             * 原因在于这个广播 是“隐式” 发送的，8.0中，静态注册的广播接收者无法接受 隐式 广播。
             * 为了解决这个问题，有两个方法：
             * 1 在Activity或其他组件中动态注册广播
             * 2 发送显示广播
             */
            case R.id.send_broadcast:
                Log.d(TAG, "sendBroadcastTv, is onClicked!!!");
//                Intent intent = new Intent();
//                intent.setAction("com.study.android.anr.Action");
//                intent.setComponent( new ComponentName( "com.study.android" ,
//                        "com.study.android.anr.MyReceiver") );
//                sendBroadcast(intent);

                Intent intent = new Intent();
                intent.setComponent(new ComponentName(ANRActivity.this, ANRReceiver.class));
                sendBroadcast(intent);
                break;
        }
    }
}