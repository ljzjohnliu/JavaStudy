package com.study.android.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    public View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("BaseFragment", "onCreateView: ");
        rootView = inflater.inflate(setLayoutResourceID(), container, false);
        setUpView();
        return rootView;
    }

    protected abstract int setLayoutResourceID();

    protected abstract void setUpView();
}
