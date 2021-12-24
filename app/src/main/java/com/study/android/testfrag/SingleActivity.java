package com.study.android.testfrag;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.fragment.SingleFragment;

public class SingleActivity extends BaseSimpleActivity {

    private SingleFragment singleFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_layout);

        if (singleFragment == null) {
            singleFragment = new SingleFragment();
        }

        // 获取fragmentmanger
        FragmentManager fragmentManager = getSupportFragmentManager();

        // 开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // 替换成fragment
        fragmentTransaction.replace(R.id.frame, singleFragment);

        // 提交
        fragmentTransaction.commitNow();

        // 直接使用链表的形式
        //        getSupportFragmentManager().beginTransaction().add(R.id.frame, singleFragment)
        //        .commitNow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (singleFragment != null) {
            singleFragment.onDestroy();
        }
    }
}
