package com.study.android.newtask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 验证这个需要设置MainActivity的launchMode="singleInstance"
 * 从ActivityC跳转到MainActivity时候再次点击跳转ActivityB，只会把它所在的栈调到前台，因为C在栈顶，所以会看到显示的C而非B，有点令人费解
 * 这是因为两个知识点：
 * 1、singleInstance启动其他Activity的时候intent默认携带了FLAG_ACTIVITY_NEW_TASK
 * 2、FLAG_ACTIVITY_NEW_TASK 官方解释：
 * Start the activity in a new task. If a task is already running for the activity you are now starting,
 * that task is brought to the foreground with its last state restored and the activity receives the new intent
 * in onNewIntent().
 * This produces the same behavior as the "singleTask" launchMode value, discussed in the previous section.
 * 以上是谷歌官网的描述，里边的意思很清楚了，当有task运行了你要运行的activity，这个task会到前台并，并且这个activity会回调onNewIntent(),
 * 而不是创建新的。
 *
 * 这里还是要看ActivityB的启动模式的，如果singleTop或者标准启动模式，那么只会把B所在的栈调到前台，C在栈顶 只会看到C
 * 如果ActivityB是singleTask的话 会执行其onNewIntent方法并且清除其上的ActivityC
 */
public class ActivityB extends BaseSimpleActivity {
    private static final String TAG = "BBBB";

    @OnClick({R.id.start_activity_c})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.start_activity_c:
                intent.setClass(this, ActivityC.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_b);
        ButterKnife.bind(this);
        Log.d(TAG, "------onCreate: ");
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
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
}