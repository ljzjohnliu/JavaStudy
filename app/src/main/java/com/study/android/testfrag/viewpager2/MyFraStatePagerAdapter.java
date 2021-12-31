package com.study.android.testfrag.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 使用FragmentPageAdapter适配器的时候，他会持久化数据，即时Fragment被系统销毁，数据也会保存在内存中，
 * 所有它不能用来加载大数据量的数据。
 *
 * 使用FragmentStatePageAdapter适配器的时候，他会销毁Fragment仅仅保存Fragment的引用，适合加载大数据量的数据。
 */
public class MyFraStatePagerAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragments;

    public MyFraStatePagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments){
        super(fragmentActivity);
        this.mFragments = fragments;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
