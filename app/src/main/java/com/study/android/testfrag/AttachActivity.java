package com.study.android.testfrag;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.study.android.R;
import com.study.android.fragment.FilmFragment;
import com.study.android.fragment.HomeFragment;
import com.study.android.fragment.MeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttachActivity extends AppCompatActivity {

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
        initTab(0);
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

    private void hideFragments(FragmentTransaction transaction) {
        if (filmFragment != null) {
            transaction.detach(filmFragment);
        }
        if (homeFragment != null) {
            transaction.detach(homeFragment);
        }

        if (meFragment != null) {
            transaction.detach(meFragment);
        }
    }
}
