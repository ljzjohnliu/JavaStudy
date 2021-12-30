package com.study.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.study.android.R;
import com.study.android.testfrag.AttachActivity;
import com.study.android.testfrag.ShowActivity;

public class FilmFragment extends BaseFragment {

    private static final String TAG = "FilmFragment";

    TextView titleView;
    TextView contentView;

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
        return R.layout.fragment_oversee_layout;
    }

    @Override
    protected void setUpView() {
        titleView = rootView.findViewById(R.id.title);
        contentView = rootView.findViewById(R.id.content);
        titleView.setOnClickListener(v -> {
            if (filmTransformInterface != null) {
                filmTransformInterface.tellHomeSomeThings("今天晚上电影《流星花园》");
            }
        });

        contentView.setOnClickListener(v -> {
            if (dataCallback != null) {
                contentView.setText(dataCallback.getHomeInfo());
            }
        });
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

    private AttachActivity.onDataCallback dataCallback;

    public void setDataCallback(AttachActivity.onDataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    public void setFilmInterface(FilmTransformInterface filmInterface) {
        filmTransformInterface = filmInterface;
    }

    private FilmTransformInterface filmTransformInterface;

    public interface FilmTransformInterface {
        void tellHomeSomeThings(String msg);
    }
}
