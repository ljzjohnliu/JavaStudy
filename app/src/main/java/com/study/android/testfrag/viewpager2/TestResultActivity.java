package com.study.android.testfrag.viewpager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestResultActivity extends BaseSimpleActivity {

    public static final int REQUEST_TEST = 10;
    public static final String RESULT_FROM_TEST = "RESULT_FROM_TEST";
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            from = getIntent().getStringExtra("from");
        }
    }

    @OnClick({R.id.return_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                new Intent();
                Intent intent = new Intent();
                intent.putExtra(RESULT_FROM_TEST, "Happy new year!" + from);
                Log.d("TestResult", "onClick: ------------ " + ("Happy new year!" + from));
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
