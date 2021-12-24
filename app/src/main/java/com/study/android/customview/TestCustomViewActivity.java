package com.study.android.customview;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCustomViewActivity extends BaseSimpleActivity {
    private static final String TAG = "TestCustomView";

    @BindView(R.id.head_view)
    HeaderView headView;
    @BindView(R.id.standard_tv)
    TextView standardTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom);
        ButterKnife.bind(this);

//        headView.setTitle("这是标题");
        headView.setLeftListener(view -> Log.d(TAG, "left icon is onClicked!"));
        headView.setRightListener(view -> Log.d(TAG, "right icon is onClicked!"));
//        getViewSize("onCreate");

        /**
         * 正确获取View宽高的方法2：Handler 或 view.post（runnable）
         * 通过post可以将一个runnable投递到消息队列的尾部，然后等待Looper调用此runnable时，View也已经初始化好了。
         */
        standardTv.post(new Runnable() {
            @Override
            public void run() {
                getViewSize("post");
            }
        });
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getViewSize("Handler post");
            }
        });
        /**
         * 正确获取View宽高的方法3：ViewTreeObserver
         * 使用ViewTreeObserver的众多回调也可以完成这个功能，比如使用OnGlobalLayoutListener这个接口。
         * 当View树的状态发生改变或者View树内部的View的可见性发生改变时，onGlobalLayout方法将被调用。
         *
         * 注意：伴随着View树的状态改变等，onGlobalLayout会被调用多次。因此需要在适当时机将监听回调移除。
         */
        ViewTreeObserver viewTreeObserver = standardTv.getViewTreeObserver();
        viewTreeObserver.addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                viewTreeObserver.removeOnGlobalFocusChangeListener(this);
                getViewSize("onGlobalFocusChanged");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
//        getViewSize("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
        getViewSize("onResume");
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

    /**
     * 正确获取View宽高的方法1：onWindowFocusChanged
     * 该方法的含义是：View已经初始化完毕了，宽/高已经准备好了，所以此时去获取宽/高是没有问题的。
     * 注意：onWindowFocusChanged会被调用多次，当activity的窗口得到焦点和失去焦点时均会被调用一次，
     * 具体来说，当activity继续执行（onResume）和暂停执行（onPause）时，onWindowFocusChanged均会被调用。
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "------onWindowFocusChanged: hasFocus = " + hasFocus);
        if (hasFocus) {
            getViewSize("onWindowFocusChanged");
        }
    }

    /**
     * View 的测绘流程是在 performTraversals() 才开始的；而这个方法的调用是在 onResume() 方法之后，
     * 所以在 onCreate() 和 onResume() 方法中拿不到 View 的宽高信息也就很容易理解了。
     * 实际上在onCreate，onStart，onResume中均无法正确得到某个View的宽高信息。因为View的measure过程和activity的生命周期方法不是同步执行的，
     * 因此无法保证Activity执行了onCreate，onStart，onResume时，某个View已经测量完毕，一旦View没有测量完毕，那么我们此时获得的宽/高就是0。
     * 总结了正确获取某个View宽高信息的四种方法
     */
    private void getViewSize(String msg) {
        Log.d(TAG, msg + " standardTv, getViewSize: height = " + standardTv.getHeight() + ", width = " +  + standardTv.getWidth());
        Log.d(TAG, msg + " standardTv, getViewSize: measured height = " + standardTv.getMeasuredHeight() + ", width = " +  + standardTv.getMeasuredHeight());
    }
}