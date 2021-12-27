package com.study.android.event;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

public class TestEventActivity extends BaseSimpleActivity {
    private static final String TAG = "TestEventActivity";

    TextView txtView;
    Button button1, button2;
    ViewGroup myLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);

        txtView = findViewById(R.id.text_view);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        myLayout = findViewById(R.id.my_layout);

        // 1.为ViewGroup布局设置监听事件
        myLayout.setOnClickListener(v -> Log.d(TAG, "点击了ViewGroup"));

        txtView.setOnClickListener(v -> Log.d(TAG, "点击了----111----txtView"));

//        txtView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouch: 执行了onTouch(), 动作是:" + event.getAction());
////                return true;
//                return false;
//            }
//        });

        button1.setOnClickListener(v -> Log.d(TAG, "点击了----222----button1"));

        button2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "button2: 执行了onTouch(), 动作是:" + event.getAction());
//                return true;
                return false;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "button2: 执行了--------onClick()------");
            }
        });

        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "button2: 执行了========onLongClick======");
                /* return true的话就不会有点击事件，return false会继续执行短按事件，不过这个也是要看Rom的，小米9上就不会执行短按事件*/
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ev.getAction = " + ev.getAction());
//        txtView.dispatchTouchEvent(ev);
//        return super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    public void onUserInteraction() {
        Log.d(TAG, "onUserInteraction: ----------");
    }

    @Override
    public void onUserLeaveHint() {
        Log.d(TAG, "onUserLeaveHint: ----------");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ----------event = " + event);
        return super.onTouchEvent(event);
    }
}