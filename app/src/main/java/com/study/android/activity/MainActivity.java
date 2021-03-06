package com.study.android.activity;

import static com.study.android.activity.TestEnum.TEST_ITEM1;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.animation.TestAnimationActivity;
import com.study.android.anr.ANRActivity;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.bitmap.TestBitmapActivity;
import com.study.android.communicate.Book;
import com.study.android.communicate.ProcessComActivity;
import com.study.android.customview.TestCustomViewActivity;
import com.study.android.event.TestEventActivity;
import com.study.android.event.TestEventActivity2;
import com.study.android.handler.HandlerActivity;
import com.study.android.handler.HandlerActivity4;
import com.study.android.handler.SyncBarrierActivity;
import com.study.android.multiprocess.TestMultiProcessActivity;
import com.study.android.newtask.ActivityB;
import com.study.android.receiver.TestBroadcastActivity;
import com.study.android.recyclerview.DemoListActivity;
import com.study.android.recyclerview.TestRVActivity;
import com.study.android.recyclerview2.TestRvActivity;
import com.study.android.service.TestServiceActivity;
import com.study.android.slideconflict.SlideConflictActivity1;
import com.study.android.slideconflict.SlideConflictActivity2;
import com.study.android.testfrag.AttachActivity;
import com.study.android.testfrag.ShowActivity;
import com.study.android.testfrag.viewpager2.ViewPager2Activity;
import com.study.android.videoview.TestDealBitmapActivity;
import com.study.android.videoview.TestSurfaceViewActivity;
import com.study.android.videoview.TestVideoViewActivity;
import com.study.android.webview.WebViewActivity;
import com.study.java.TestABCD;

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
            R.id.test_new_task, R.id.test_webview, R.id.test_more_process, R.id.test_animation,
            R.id.test_bitmap, R.id.slide_conflict1, R.id.slide_conflict2})
    public void onJumpClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.test_activity_life:
                // ????????????????????????????????????
//                intent.setClass(this, ActivityA.class);
                intent.setComponent(new ComponentName("com.study.android", "com.study.android.lifecycle.ActivityA"));
                intent.putExtra("enum", TEST_ITEM1);
                Log.d(TAG, "onJumpClick: TEST_ITEM1 = " + TEST_ITEM1);
                break;
            case R.id.test_service_life:
                intent.setClass(this, TestServiceActivity.class);
                break;
            case R.id.test_receiver:
                intent.setClass(this, TestBroadcastActivity.class);
                break;
            case R.id.test_activity_anr:
                intent.setClass(this, ANRActivity.class);
                Book book = new Book(188, "halo", "????????????");
                intent.putExtra("book", book);
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
            case R.id.test_more_process:
                intent.setClass(this, TestMultiProcessActivity.class);
                break;
            case R.id.test_animation:
                intent.setClass(this, TestAnimationActivity.class);
                break;
            case R.id.test_bitmap:
                intent.setClass(this, TestBitmapActivity.class);
                break;
            case R.id.slide_conflict1:
                intent.setClass(this, SlideConflictActivity1.class);
                break;
            case R.id.slide_conflict2:
                intent.setClass(this, SlideConflictActivity2.class);
                break;
        }
        startActivity(intent);
//        finish();
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
     * ????????????????????? UI ???????????? viewRootImpl.java ???????????????
     * <p>
     * void checkThread() {
     *     // ???????????? viewRootImpl.java ????????????
     *     if (mThread != Thread.currentThread()) {
     *         throw new CalledFromWrongThreadException(
     *         "Only the original thread that created a view hierarchy can touch its views.");
     *     }
     * }
     * ????????? Activity ?????????viewRootImpl ??????????????? onCreate ?????????onResume ?????????
     * ??????????????????????????????????????????????????????????????????????????????????????????:
     * 1????????????????????? viewRootImpl.java ?????????????????????
     * 2??????????????????????????? onResume ??????????????? onCreate ???????????????????????????????????? viewRootImpl ??????????????????
     */
    private void updateUIWithThread(String msg, boolean isDelay) {
        /**
         * ???????????????setText??????????????????requestLayout????????????????????????performLayout?????????mHandlingLayoutInLayoutRequest???????????????true???
         * ???????????????????????????????????????setText?????????????????????requestLayout???????????????????????????????????????????????????mHandlingLayoutInLayoutRequest??????true???
         * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????update????????????setText????????????????????????????????????
         *
         * ????????????????????????????????????
         */
//        titleTv.setText("Hello!!!");
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)titleTv.getLayoutParams();
//        params.topMargin = 20;
//        titleTv.setLayoutParams(params);
        new Thread() {
            @Override
            public void run() {
                titleTv.setText("??????" + msg + "????????????????????????UI");
                Log.d(TAG, "run: thread id = " + Thread.currentThread().getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ");
                    }
                });
                if (isDelay) {
                    try {
                        // ?????? onResume ??????????????? viewRootImpl ???????????????
                        Thread.sleep(1000); // ---------- ??????????????????
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                titleTv.setText("??????" + msg + "????????????????????????UI");
//                titleTv.invalidate();
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int psNum = Runtime.getRuntime().availableProcessors();
        Log.d(TAG, "onCreate: psNum = " + psNum);
        Log.d(TAG, "onCreate: thread id = " + Thread.currentThread().getId());
//        updateUIWithThread("onCreate", true);
        new Thread() {
            @Override
            public void run() {
                titleTv.invalidate();
            }
        }.start();

        SparseArray sparseArray1;
        SparseIntArray sparseArray = new SparseIntArray();
        sparseArray.put(1, 2);
        Intent intent = getIntent();
        intent.putExtra("key", 90);
        intent.putExtra("bundle", new Bundle());
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
        //???????????????????????????Activity???????????????????????????????????????????????????????????????????????????
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

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: ------------------");
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
}