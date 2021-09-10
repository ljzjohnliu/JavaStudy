package com.study.android.anr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ANRReceiver extends BroadcastReceiver {

    /**
     * BroadcastReceiver未在10秒内完成相关的处理
     * 这个理解可能有误，跟Activity一样，
     * 以下代码在 点击事件 中 sleep 了 20秒,会出现 ANR 吗？
     * 答案是：可能会，也可能不会
     * (1)不会出现ANR的情况：
     * 如果点击了”测试按钮“，之后的30s之内，我们没有进行手动触摸操作（即没有进行任何操作），则不会发生ANR，
     * 这是因为这段代码里面的sleep休眠了线程，代码里面的更新操作根本没有在 sleep的时候被触发（处于休眠状态），
     * 也就没有了发送请求的前提条件，所以并没有发生ANR。
     * <p>
     * (2)会出现ANR的情况：
     * 如果用户手动进行了触摸操作（比如点击屏幕或者按返回键），这里可以点击另一个控件触发其点击事件，5s内无响应，
     * 相当于有一个请求的事件了，而主线程又被休眠了，超过了规定的时间就会触发ANR提示。
     *
     * Service中只要sleep时间过长就会ANR，跟后续有没有事件没关系
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAG", "ANRReceiver, onReceive: ");
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        try {
            Log.d("TAG", "Simulate network requests start");
            Thread.sleep(20000);
            Log.d("TAG", "Simulate network requests end");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("TAG", "Simulate network requests e = " + e);
        }
    }
}
