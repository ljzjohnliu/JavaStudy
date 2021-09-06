package com.study.android.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DecoreView extends View {

    private static final String TAG = "DecoreView";

    public DecoreView(Context context) {
        super(context);
    }

    public DecoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DecoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DecoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getDispatchSize(int patch) {
        return patch == 0 ? "点击====" : patch == 1 ? "抬起====" : patch == 2 ? "移动====" : "其他====";
    }

    /**
     * 控制自己
     * dispatchTouchEvent
     * 返回true  自己也消费不到,这里指的是onTouchEvent以及onClick等事件不会触发,并且上一层的ViewGroup activity的onTouch也不会执行
     * 返回false 自己不消费，但是上一层onTouchEvent以及onClick等事件会触发
     * super.dispatchTouchEvent(event) 默认false 自己可以消费
     * 但是如果返回false ， 就一定要调用super.dispatchTouchEvent(event)方法哦
     * 否则的话onTouchEvent方法还是收不到
     * 如果返回true就不用啦，因为已经被强制拦截啦
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent ----event.getAction = " + event.getAction());
        return super.dispatchTouchEvent(event);
//        return false;
    }

    /**
     * onTouchEvent
     * 返回true  自己消费 不向上传递,此时该view执行onTouchEvent事件,不会执行onclick
     * 返回false 向上传递，上一层的viewGroup将执行所有的事件
     * 返回super.onTouchEvent(event) 默认为false,
     * 此时该view执行所有事件以及onClick点击事件,上一层的viewGroup将不会执行OnTouchEvent以及onClick事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent =================" + getDispatchSize(event.getAction()));
        return super.onTouchEvent(event);
//        return true;
    }
}
