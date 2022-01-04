package com.study.android.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.anr.ANRActivity;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.communicate.ProcessComActivity;
import com.study.android.customview.TestCustomViewActivity;
import com.study.android.event.TestEventActivity;
import com.study.android.event.TestEventActivity2;
import com.study.android.handler.HandlerActivity;
import com.study.android.handler.HandlerActivity4;
import com.study.android.handler.SyncBarrierActivity;
import com.study.android.newtask.ActivityB;
import com.study.android.receiver.TestBroadcastActivity;
import com.study.android.recyclerview.DemoListActivity;
import com.study.android.recyclerview.TestRVActivity;
import com.study.android.recyclerview2.TestRvActivity;
import com.study.android.service.TestServiceActivity;
import com.study.android.testfrag.AttachActivity;
import com.study.android.testfrag.ShowActivity;
import com.study.android.testfrag.viewpager2.ViewPager2Activity;
import com.study.android.videoview.TestDealBitmapActivity;
import com.study.android.videoview.TestSurfaceViewActivity;
import com.study.android.videoview.TestVideoViewActivity;
import com.study.android.webview.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseSimpleActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;

    @OnClick({R.id.test_activity_life, R.id.test_service_life, R.id.test_receiver, R.id.test_event, R.id.test_activity_anr,
            R.id.test_process_comm, R.id.test_handler_barrier, R.id.test_handler, R.id.test_custom_view,
            R.id.test_video_view, R.id.test_surface_view, R.id.test_deal_bitmap, R.id.test_fragment,
            R.id.test_view_pager2, R.id.test_recycler_view, R.id.test_recycler_view2,
            R.id.test_new_task, R.id.test_webview})
    public void onJumpClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.test_activity_life:
                // 此方法不能实现跨应用调用
//                intent.setClass(this, ActivityA.class);
                intent.setComponent(new ComponentName("com.study.android", "com.study.android.lifecycle.ActivityA"));
                break;
            case R.id.test_service_life:
                intent.setClass(this, TestServiceActivity.class);
                break;
            case R.id.test_receiver:
                intent.setClass(this, TestBroadcastActivity.class);
                break;
            case R.id.test_activity_anr:
                intent.setClass(this, ANRActivity.class);
                break;
            case R.id.test_event:
                intent.setClass(this, TestEventActivity2.class);
                break;
            case R.id.test_process_comm:
                intent.setClass(this, ProcessComActivity.class);
                break;
            case R.id.test_handler:
                intent.setClass(this, HandlerActivity.class);
                break;
            case R.id.test_handler_barrier:
                intent.setClass(this, SyncBarrierActivity.class);
                break;
            case R.id.test_custom_view:
                intent.setClass(this, TestCustomViewActivity.class);
                break;
            case R.id.test_video_view:
                intent.setClass(this, TestVideoViewActivity.class);
                break;
            case R.id.test_surface_view:
                intent.setClass(this, TestSurfaceViewActivity.class);
                break;
            case R.id.test_deal_bitmap:
                intent.setClass(this, TestDealBitmapActivity.class);
                break;
            case R.id.test_fragment:
                intent.setClass(this, com.study.android.testfrag.MainActivity.class);
                break;
            case R.id.test_view_pager2:
                intent.setClass(this, ViewPager2Activity.class);
                break;
            case R.id.test_recycler_view:
//                intent.setClass(this, TestRecyclerViewActivity.class);
                intent = new Intent(this, DemoListActivity.class);
                intent.putExtra(DemoListActivity.EXTRA_USE_PRE_CACHE, true);
                break;
            case R.id.test_recycler_view2:
                intent.setClass(this, TestRvActivity.class);
                break;
            case R.id.test_new_task:
                intent.setClass(this, ActivityB.class);
                break;
            case R.id.test_webview:
//                intent.setClass(this, WebViewActivity.class);
//                WebViewActivity.openWebViewActivity(this, "https://www.baidu.com");
                WebViewActivity.openWebViewActivity(this, "file:///android_asset/ljz_test.html");
                return;
        }
        startActivity(intent);
    }

    @OnClick({R.id.test_thread_updateui})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_thread_updateui:
                updateUIWithThread("Button click", false);
                break;
        }
    }

    /**
     * 子线程不能更新 UI 的限制是 viewRootImpl.java 内部限制了
     *
     * void checkThread() {
     *     // 该方法是 viewRootImpl.java 内部代码
     *     if (mThread != Thread.currentThread()) {
     *         throw new CalledFromWrongThreadException(
     *                 "Only the original thread that created a view hierarchy can touch its views.");
     *     }
     * }
     * 对组件 Activity 而言，viewRootImpl 的初始化在 onCreate 之后，onResume 之后。
     * 如果你的子线程更新代码在满足下面的条件下，那么它可以顺利运行:
     * 1、修改应用层的 viewRootImpl.java 源码，解除限制
     * 2、把你更新代码写在 onResume 之前，例如 onCreate 里面，且，更新之际要赶在 viewRootImpl 初始化之前。
     */
    private void updateUIWithThread(String msg, boolean isDelay) {
        /**
         * 第一次设置setText时会导致调用requestLayout，这就导致会执行performLayout，然后mHandlingLayoutInLayoutRequest就被置为了true。
         * 这时立马开启子线程再次调用setText时又会导致调用requestLayout，但这时很多情况下还在布局中，所以mHandlingLayoutInLayoutRequest还是true，
         * 这样就导致了不检查线程。不过这种情况下只有大多数时候能更新成功，有时也会报错。如果把update中第一个setText去掉，那么百分百会报错。
         *
         * 实际操作下来并没有成功！
         */
//        titleTv.setText("Hello!!!");
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)titleTv.getLayoutParams();
//        params.topMargin = 20;
//        titleTv.setLayoutParams(params);
        new Thread() {
            @Override
            public void run() {
                titleTv.setText("我在" + msg + "中调用子线程更新UI");
                Log.d(TAG, "run: thread id = " + Thread.currentThread().getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ");
                    }
                });
                if (isDelay) {
                    try {
                        // 等待 onResume 执行完，让 viewRootImpl 初始化完成
                        Thread.sleep(1000); // ---------- 这里，看这里
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                titleTv.setText("我在" + msg + "中调用子线程更新UI");
//                titleTv.invalidate();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: thread id = " + Thread.currentThread().getId());
//        updateUIWithThread("onCreate", true);
        new Thread() {
            @Override
            public void run() {
                titleTv.invalidate();
            }
        }.start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "------onNewIntent: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
//        updateUIWithThread("onStart", true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
        //以下这样写首次进入Activity时候没有问题，如果打开其他界面再返回就会崩溃！！！
//        updateUIWithThread("onResume", false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: ");
    }
}