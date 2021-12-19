package com.study.android.customview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;

import butterknife.ButterKnife;

public class TestCustomViewActivity extends AppCompatActivity {
    private static final String TAG = "TestCustomView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom);
        ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}