package com.study.android.testfrag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.fragment.FilmFragment;
import com.study.android.fragment.HomeFragment;
import com.study.android.fragment.MeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttachActivity extends BaseSimpleActivity {

    private static final String TAG = "AttachActivity";
    private HomeFragment homeFragment;
    private FilmFragment filmFragment;
    private MeFragment meFragment;

    @BindView(R.id.hp_text)
    TextView hpText;
    @BindView(R.id.film_text)
    TextView filmText;
    @BindView(R.id.me_text)
    TextView meText;

    @OnClick({R.id.hp_text, R.id.film_text, R.id.me_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hp_text:
                initTab(0);
                break;
            case R.id.film_text:
                initTab(1);
                break;
            case R.id.me_text:
                initTab(2);
                break;
        }
    }

    // 注意fragment 的生命周期变化 重新走了
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_layout);
        ButterKnife.bind(this);
        Log.d(TAG, "------onCreate: ");
        initTab(0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "------onNewIntent: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: ");
    }

    private void initTab(int i) {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        clearSelection();
        switch (i) {
            case 0:
                hpText.setTextColor(Color.RED);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                } else {
                    transaction.attach(homeFragment);
                }
                break;
            case 1:
                filmText.setTextColor(Color.RED);
                if (filmFragment == null) {
                    filmFragment = new FilmFragment();
                    transaction.add(R.id.content, filmFragment);
                } else {
                    transaction.attach(filmFragment);
                }
                break;
            case 2:
                meText.setTextColor(Color.RED);
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.content, meFragment);
                } else {
                    transaction.attach(meFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();

    }

    private void clearSelection() {
        hpText.setTextColor(Color.BLACK);
        filmText.setTextColor(Color.BLACK);
        meText.setTextColor(Color.BLACK);
    }

    /**
     * 使用detach()会将view从viewtree中删除,和remove()不同,此时fragment的状态依然保持着,
     * 在使用attach()时会再次调用onCreateView()来重绘视图,注意使用detach()后fragment.isAdded()方法将返回false,
     * 在使用attach()还原fragment后isAdded()会依然返回false(需要再次确认)
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (filmFragment != null) {
            Log.d(TAG, "hideFragments: detach filmFragment");
            transaction.detach(filmFragment);
        }
        if (homeFragment != null) {
            Log.d(TAG, "hideFragments: detach homeFragment");
            transaction.detach(homeFragment);
        }

        if (meFragment != null) {
            transaction.detach(meFragment);
        }
    }
}
