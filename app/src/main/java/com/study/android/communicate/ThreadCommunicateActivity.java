package com.study.android.communicate;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 常用的Android线程间通信方式：
 * 1、Handler，Demo中有专门的Handler的demo，这里就不赘述了
 * 2、View.post
 * 3、Activity.runOnUiThread
 * 4、EventBus
 * 5、AsyncTask
 *
 * 这里主要验证子线程刷新UI线程的场景
 */
public class ThreadCommunicateActivity extends AppCompatActivity {
    private final String TAG = "ThreadCommunicate";

    private Activity mActivity;
    @BindView(R.id.test_tv)
    TextView testTv;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_communicate);
        ButterKnife.bind(this);
        mActivity = this;
        Log.d(TAG, "onCreate getPids = " + Utils.getPids());

        /**
         * 这里有一个问题：就是countDownTimer放到Thread中去创建，倒计时是不生效的，不清楚原因，待考究？！
         */
        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String value = String.valueOf((int) (millisUntilFinished / 1000));
                Log.d(TAG, "onTick: value = " + value + ", getPids = " + Utils.getPids());
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: getPids = " + Utils.getPids());
            }
        };
        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "run: ***********************");
                countDownTimer.start();
            }
        }.start();
    }

    @OnClick({R.id.async_task, R.id.view_post, R.id.run_on_uithread})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.async_task:
                /**
                 * 步骤2：创建AsyncTask子类的实例对象（即 任务实例）
                 * 注：AsyncTask子类的实例必须在UI线程中创建
                 */
                MyTask mTask = new MyTask();
                /**
                 * 步骤3：手动调用execute(Params... params) 从而执行异步线程任务
                 * 注：
                 *    a. 必须在UI线程中调用
                 *    b. 同一个AsyncTask实例对象只能执行1次，若执行第2次将会抛出异常
                 *    c. 执行任务中，系统会自动调用AsyncTask的一系列方法：onPreExecute() 、doInBackground()、onProgressUpdate() 、onPostExecute()
                 *    d. 不能手动调用上述方法
                 */
                mTask.execute("Test",  "AsyncTask");
                break;
            case R.id.view_post:
                new Thread() {
                    @Override
                    public void run() {
                        Log.d(TAG, "子线程1 run: getPids = " + Utils.getPids());
                        //Only the original thread that created a view hierarchy can touch its views.
//                        testTv.setText("子线程采用View.post刷新UI 111");
                        testTv.post(new Runnable() {
                            @Override
                            public void run() {
                                testTv.setText("子线程采用View.post刷新UI 222");
                            }
                        });
                    }
                }.start();
                break;
            case R.id.run_on_uithread:
                new Thread() {
                    @Override
                    public void run() {
                        Log.d(TAG, "子线程2 run: getPids = " + Utils.getPids());
                        //Only the original thread that created a view hierarchy can touch its views.
//                        testTv.setText("子线程采用runOnUiThread刷新UI 111");
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                testTv.setText("子线程2采用runOnUiThread刷新UI 222");
                            }
                        });
                    }
                }.start();
                break;
        }
    }

    /**
     * 步骤1：创建AsyncTask子类
     * 注：
     *   a. 继承AsyncTask类
     *   b. 为3个泛型参数指定类型；若不使用，可用java.lang.Void类型代替
     *   c. 根据需求，在AsyncTask子类内实现核心方法
     */

    private class MyTask extends AsyncTask<String, Integer, String> {
        private static final String TAG = "MyTask";

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        // 注：它是在主线程的
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: " + Utils.getPids());
        }

        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 注：必须复写，它是在主线程的
        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "doInBackground: params length = " + params.length + ", getPids = " + Utils.getPids());
            for (String str : params) {
                Log.d(TAG, "doInBackground: str = " + str);
            }
            // 自定义的线程任务

            // 可调用publishProgress（）显示进度, 之后将执行onProgressUpdate（）
            publishProgress(100);
            return "----doInBackground----";
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        // 注：根据需求复写，它是在主线程的
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            Log.d(TAG, "onProgressUpdate, getPids = " + Utils.getPids());
            for (Integer integer : progresses) {
                Log.d(TAG, "onProgressUpdate: integer = " + integer);
            }
        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        // 注：必须复写，从而自定义UI操作，它是在主线程的
        @Override
        protected void onPostExecute(String result) {
            // UI操作
            Log.d(TAG, "onPostExecute, result = " + result + ", getPids = " + Utils.getPids());
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态
        @Override
        protected void onCancelled() {
            Log.d(TAG, "onCancelled, getPids = " + Utils.getPids());
        }
    }
}