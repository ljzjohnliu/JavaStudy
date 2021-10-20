package com.study.android.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;

public class SingleFragment extends BaseFragment {
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_single_layout;
    }

    public static SingleFragment newInstance(AppCompatActivity activity) {
        SingleFragment fragment = new SingleFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void setUpView() {

    }
}
