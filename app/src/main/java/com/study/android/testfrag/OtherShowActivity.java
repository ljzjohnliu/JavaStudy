package com.study.android.testfrag;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.fragment.BaseFragment;
import com.study.android.fragment.FilmFragment;
import com.study.android.fragment.HomeFragment;
import com.study.android.fragment.MeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtherShowActivity extends BaseSimpleActivity {
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
                clearSelection();
                hpText.setTextColor(Color.RED);
                showFragment(homeFragment);
                break;
            case R.id.film_text:
                clearSelection();
                filmText.setTextColor(Color.RED);
                showFragment(filmFragment);
                break;
            case R.id.me_text:
                clearSelection();
                meText.setTextColor(Color.RED);
                showFragment(meFragment);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_layout);
        ButterKnife.bind(this);

        addFragments();
        // 指定默认显示第一个界面，可修改
        onClick(hpText);
    }

    private void addFragments() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.content, homeFragment);
        }

        if (filmFragment == null) {
            filmFragment = new FilmFragment();
            transaction.add(R.id.content, filmFragment);
        }

        if (meFragment == null) {
            meFragment = new MeFragment();
            transaction.add(R.id.content, meFragment);
        }
        transaction.commitNow();
    }

    private void showFragment(BaseFragment baseFragment) {
        hideAllFragments();
        if (baseFragment != null && baseFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().show(baseFragment).commitNow();
        }
    }

    protected void hideAllFragments() {
        List<Fragment> allFragment = getSupportFragmentManager().getFragments();
        if (allFragment.size() > 0) {
            for (Fragment fragment : allFragment) {
                if (fragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction().hide(fragment).commitNow();
                }
            }
        }
    }

    private void clearSelection() {
        hpText.setTextColor(Color.BLACK);
        filmText.setTextColor(Color.BLACK);
        meText.setTextColor(Color.BLACK);
    }
}
