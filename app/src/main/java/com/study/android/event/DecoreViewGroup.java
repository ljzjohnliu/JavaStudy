package com.study.android.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class DecoreViewGroup extends ViewGroup {

    private static final String TAG = "DecoreViewGroup";

    public DecoreViewGroup(Context context) {
        super(context);
    }

    public DecoreViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DecoreViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DecoreViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int layoutWidth = r - l;
        int left = 0;
        int right = 0;
        int top = 0;
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            // 换行：比较right，right如果大于Layout宽度，那么要换行
            right = left + view.getMeasuredWidth();
            if (right > layoutWidth) {
                left = 0;
                right = left + view.getMeasuredWidth();
                top += view.getMeasuredHeight();
            }
            getChildAt(i).layout(left, top, right, top + view.getMeasuredHeight());
            left += view.getWidth();
        }
    }

    /**
     * onInterceptTouchEvent
     * 返回true  自己消费，会执行自己的onTouch事件以及点击事件。 子类收不到
     * 返回false或super的话 会dispatch事件到子类
     * 如果没有子类的话，会执行自己的onTouch事件以及点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent event.getAction = " + event.getAction()/* + ", onInterceptTouchEvent = " + super.onInterceptTouchEvent(event)*/);
        return super.onInterceptTouchEvent(event);
//        return true;
    }


    /**
     * dispatchTouchEvent
     * 返回true  事件终止分发，并且自己以及Activity也消费不到，这里指的是onTouchEvent以及onClick等事件不会触发
     * 返回false 自己不消费，上一层的activity能消费，activity的onTouchEvent会执行
     * 返回super.dispatchTouchEvent(event) 默认为false
     * 但是如果返回false，就一定要在return之前调用super.dispatchTouchEvent(event)方法哦
     * 否则的话onTouchEvent方法还是收不到
     * super.dispatchTouchEvent(event)之后才能进入onInterceptTouchEvent！
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent getAction = " + event.getAction());
        return super.dispatchTouchEvent(event);
//        return false;
    }

    /**
     * onTouchEvent
     * 返回true  消费事件，activity的onTouchEvent不会执行，并且自己的点击事件也不会触发
     * 返回false 自己不消费 执行DOWN事件之后不再执行，向上传递，包括DOWN事件以及后续的所有事件activity的onTouchEvent会执行
     *
     * 返回super.onTouchEvent(event) 默认为false , activity的onTouchEvent不会执行，返回 super.onTouchEvent(event)才能收到点击事件！！
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent event.getAction = " + event.getAction());
        return super.onTouchEvent(event);
//        return false;
    }
}
