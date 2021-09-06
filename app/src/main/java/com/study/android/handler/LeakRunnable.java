package com.study.android.handler;

import android.os.Handler;
import android.os.Message;

public class LeakRunnable implements Runnable {
    private Handler handler;
    private Message msg;

    public LeakRunnable(Handler handler) {
        this.handler = handler;
        msg = new Message();
    }

    @Override
    public void run() {
//        MessageQueue_Message_Leak();
        Thread_Handler_Leak();
    }

    public void MessageQueue_Message_Leak() {
        /**
         * Message中持有Handler对象, 在handler发送消息时会把持有的handler引用指向发送自己的handler，在源码中这个对象名叫target,
         * 这也是多个Handler的情况下，Looper能取出Message给发送该消息的Handler!
         */
        msg.what = 0;
        handler.sendMessageDelayed(msg, 1000 * 60 * 10);
    }

    public void Thread_Handler_Leak() {
        while (true) {
            try {
                Thread.sleep(1000 * 10 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
