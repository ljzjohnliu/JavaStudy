package com.study.android.testfrag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.testfrag.viewpager.ViewPagerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseSimpleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.viewpager_btn, R.id.show_btn, R.id.replace_btn, R.id.other_show_btn, R.id.attach_btn, R.id.other_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewpager_btn:
                /*SharedPreferences mPreferences =
                        PreferenceManager.getDefaultSharedPreferences
                                (MainActivity.this);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("Main", true);
                editor.apply();

                SharedPreferences mPreferences1 =
                        PreferenceManager.getDefaultSharedPreferences
                                (MainActivity.this);
                Boolean misStopButtonClick = mPreferences1.getBoolean("Main",
                        false);

                if (misStopButtonClick) {
                    Toast.makeText(MainActivity.this, "1111111111", Toast
                            .LENGTH_SHORT).show();
                }
                Log.e("----------hly", String.valueOf(misStopButtonClick));*/

                startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
                break;
            case R.id.show_btn:
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
                break;
            case R.id.replace_btn:
                startActivity(new Intent(MainActivity.this, ReplaceActivity.class));
                break;
            case R.id.other_show_btn:
                startActivity(new Intent(MainActivity.this, OtherShowActivity.class));
                break;
            case R.id.attach_btn:
                startActivity(new Intent(MainActivity.this, AttachActivity.class));
                break;
            case R.id.other_btn:
                startActivity(new Intent(MainActivity.this, SingleActivity.class));
                break;
        }
    }
}
