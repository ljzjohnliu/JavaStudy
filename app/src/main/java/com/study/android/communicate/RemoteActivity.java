package com.study.android.communicate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.ProcessUtil;

/**
 * Android 跨进程通信大总结
 * 1、activity进程间通信
 * 2、activity、service 进程间通信 AIDL
 * 3、ContentProvider跨进程
 * 4、广播跨进程
 * 5、SharedPreferences 跨进程(类似MMKV一样原理)
 * 6、文件共享跨进程
 * 7、Socket跨进程通信
 * 8、Bundle跨进程
 */
public class RemoteActivity extends BaseSimpleActivity {

    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        titleTv = findViewById(R.id.title);
        titleTv.setText(ProcessUtil.getCurrentProcessName());
        String name = getIntent().getStringExtra(ProcessComActivity.KEY_FROM);
        titleTv.setText("来自Main的value:" + name);

        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: title!!! this = " + this);
                Intent intent = new Intent();
                intent.putExtra(ProcessComActivity.KEY_FROM, "remote activity");
                setResult(ProcessComActivity.RESULT_FROM_REMOTE, intent);
                finish();
            }
        });
    }
}