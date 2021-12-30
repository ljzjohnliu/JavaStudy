package com.study.android.fragment;

import android.content.Context;
import android.os.Bundle;
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
        Log.e(TAG, "onCreate");
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void setUpView() {
        Log.d(TAG, "setUpView: intentDataCallback = " + dataCallback);
        titleView = rootView.findViewById(R.id.title);
        titleView.setText(msgFromFilm);
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

    private String msgFromFilm = "等待电影界面的结果！";

    public void receiverThings(String msg) {
        Log.d(TAG, "receiverThings: msg = " + msg);
        msgFromFilm = msg;
    }

    public String getHomeInfo() {
        Log.d(TAG, "getHomeInfo: -------------");
        return "我是来自首页的信息！";
    }
}
