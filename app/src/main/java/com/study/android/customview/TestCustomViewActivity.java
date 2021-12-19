package com.study.android.customview;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCustomViewActivity extends AppCompatActivity {
    private static final String TAG = "TestCustomView";

    @BindView(R.id.head_view)
    HeaderView headView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom);
        ButterKnife.bind(this);

        headView.setTitle("这是标题");
        headView.setLeftListener(view -> Log.d(TAG, "left icon is onClicked!"));
        headView.setRightListener(view -> Log.d(TAG, "right icon is onClicked!"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}