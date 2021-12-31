package com.study.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.study.android.R;
import com.study.android.testfrag.ShowActivity;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    private ShowActivity.onIntentDataCallback dataCallback;

    TextView titleView;
    String msg = "默认值";

    public HomeFragment() {
        Log.d(TAG, "HomeFragment: ----");
    }

    /**
     * 为什么Fragment传值需要通过setArguments（）？
     * setArguments方法必须在fragment创建以后，添加给Activity前完成。千万不要，首先调用了add，然后设置arguments。
     * 因为如果Fragment意外销毁，最终会通过反射无参构造实例化一个新的Fragment(这也是为什么Fragment必须要有无参构造器的原因)，
     * 并且给mArguments初始化为原先的值，而原来的Fragment实例的数据都丢失了，并重新进行了初始化
     * 通过上面的分析，我们可以知道Activity重新创建时，会重新构建它所管理的Fragment，原先的Fragment的字段值将会全部丢失，
     *
     * 但是通过Fragment.setArguments(Bundle bundle)方法设置的bundle会保留下来。所以尽量使用Fragment.setArguments(Bundle bundle)方式来传递参数
     */
    public HomeFragment(String args) {
        msg = args;
        Log.d(TAG, "HomeFragment: ---- msg = " + msg);
    }

    public void setDataCallback(ShowActivity.onIntentDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            msg = bundle.getString("EXTRA_MSG");
        }
        Log.e(TAG, "onCreate-------- msg = " + msg);
//        getChildFragmentManager();
//        getFragmentManager();
//        getParentFragmentManager();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void setUpView() {
        Log.d(TAG, "setUpView: intentDataCallback = " + dataCallback);
        titleView = rootView.findViewById(R.id.title);
        titleView.setText(TextUtils.isEmpty(msgFromFilm) ? msg : msgFromFilm);
        titleView.setOnClickListener(v -> {

        });
        if (dataCallback != null) {
            dataCallback.onIntentData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d(TAG, "onHiddenChanged: ----hidden = " + hidden);
        if (hidden) {
            //隐藏时所作的事情
        } else {
            //显示时所作的事情
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: ----isVisibleToUser = " + isVisibleToUser);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "------onSaveInstanceState: outState = " + outState);
    }

    private String msgFromFilm;

    public void receiverThings(String msg) {
        Log.d(TAG, "receiverThings: msg = " + msg);
        msgFromFilm = msg;
    }

    public String getHomeInfo() {
        Log.d(TAG, "getHomeInfo: -------------");
        return "我是来自首页的信息！";
    }
}
