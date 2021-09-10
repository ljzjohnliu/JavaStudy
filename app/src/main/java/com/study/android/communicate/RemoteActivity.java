package com.study.android.communicate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.activity.MainActivity;
import com.study.android.utils.ProcessUtil;

/**
 * Android 跨进程通信大总结
 * 1、activity进程间通信
 * 2、activity、service 进程间通信 AIDL
 * 3、SharedPreferences 跨进程
 * 4、广播跨进程
 * 5、ContentProvider跨进程
 */
public class RemoteActivity extends AppCompatActivity {

    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        titleTv = findViewById(R.id.title);
        titleTv.setText(ProcessUtil.getCurrentProcessName());
        String name = getIntent().getStringExtra(MainActivity.KEY_FROM);
        titleTv.setText("来自Main的value:" + name);

        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: title!!! this = " + this);
                Intent intent = new Intent();
                intent.putExtra(MainActivity.KEY_FROM, "remote activity");
                setResult(MainActivity.RESULT_FROM_REMOTE, intent);
                finish();
            }
        });
    }
}