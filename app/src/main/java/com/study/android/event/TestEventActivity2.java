package com.study.android.event;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;

public class TestEventActivity2 extends AppCompatActivity {
    private static final String TAG = "TestEventActivity";

    DecoreView decoreView;
    Button button2;
    ViewGroup myLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event2);

        decoreView = (DecoreView) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        myLayout = (DecoreViewGroup) findViewById(R.id.my_layout);

        // 1.为ViewGroup布局设置监听事件
        myLayout.setOnClickListener(v -> Log.d(TAG, "点击了ViewGroup"));

        // 2. 为按钮1设置监听事件
        decoreView.setOnClickListener(v -> Log.d(TAG, "点击了decoreView"));

        // 1. 注册Touch事件监听setOnTouchListener 且 在onTouch()返回false
        button2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: 执行了onTouch(), 动作是:" + event.getAction());
                return true;
            }
        });
        // 2. 注册点击事件OnClickListener()
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 执行了onClick()");
            }
        });

    }

    /**
     * 总结dispatchTouchEvent
     * @param ev 事件
     * @return
     * 如果该方法中不调用super.dispatchTouchEvent(ev)，无论return true or false，事件都被消费了，不会分发给ViewGroup和View，Activity的onTouchEvent也不会执行！
     * 注意并不是return值影响了事件分发，而是super.dispatchTouchEvent(ev)是否调用影响了事件分发！！！
     *
     * 调用super.dispatchTouchEvent(ev)会使事件分发至ViewGroup，要想清楚细节可以看源码。
     *
     * if (getWindow().superDispatchTouchEvent(ev)) {
     *         return true;
     *         // 若getWindow().superDispatchTouchEvent(ev)的返回true
     *         // 则Activity.dispatchTouchEvent（）就返回true，则方法结束。即 ：该点击事件停止往下传递 & 事件传递过程结束
     *         // 否则：继续往下调用Activity.onTouchEvent
     * }
     * 其中：getWindow().superDispatchTouchEvent(ev)
     * 说明：a. getWindow() = 获取Window类的对象
     *      b. Window类是抽象类，其唯一实现类 = PhoneWindow类
     *      c. Window类的superDispatchTouchEvent() = 1个抽象方法，由子类PhoneWindow类实现
     *
     * 内部实现即： mDecor.superDispatchTouchEvent(event)
     * 定义：属于顶层View（DecorView）
     * 说明：a. DecorView类是PhoneWindow类的一个内部类
     *      b. DecorView继承自FrameLayout，是所有界面的父类
     *      c. FrameLayout是ViewGroup的子类，故DecorView的间接父类 = ViewGroup
     *
     *   public boolean superDispatchTouchEvent(MotionEvent event) {
     *       return super.dispatchTouchEvent(event);
     *       // 调用父类的方法 = ViewGroup的dispatchTouchEvent()
     *       // 即将事件传递到ViewGroup去处理，详细请看后续章节分析的ViewGroup的事件分发机制
     *   }
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ev.getAction = " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ---------- event.getAction() = " + + event.getAction());
        return super.onTouchEvent(event);
    }
}