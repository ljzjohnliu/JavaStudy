package com.study.android.testfrag.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.study.android.R;
import com.study.android.fragment.FilmFragment;
import com.study.android.fragment.HomeFragment;
import com.study.android.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment 完整生命周期：
 * onAttach -> onCreate -> onCreatedView -> onActivityCreated -> onStart -> onResume -> onPause -> onStop -> onDestroyView -> onDestroy -> onDetach
 *
 * 在没有添加懒加载之前，只要使用 add+show+hide 的方式控制并显示 Fragment, 那么不管 Fragment 是否嵌套，在初始化后，如果只调用了add+show，同级下的 Fragment 的相关生命周期函数都会被调用。
 * 且调用的生命周期函数如下所示：
 * onAttach -> onCreate -> onCreatedView -> onActivityCreated -> onStart -> onResume
 *
 * 使用FragmentPagerAdapter会预加载后一个Fragment（表现为onAttach、onCreate、onActivityCreated、onStart会预先执行）
 */
public class ViewPagerActivity extends AppCompatActivity {
    private List<Fragment> list;
    private FraPagerAdapter adapter;

    @BindView(R.id.myViewPager)
    ViewPager myViewPager;
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
                myViewPager.setCurrentItem(0);
                break;
            case R.id.film_text:
                myViewPager.setCurrentItem(1);
                break;
            case R.id.me_text:
                myViewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_layout);
        ButterKnife.bind(this);
        initViewPager();
    }


    private void initViewPager() {
        //绑定点击事件
        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        hpText.setTextColor(Color.RED);
                        filmText.setTextColor(Color.BLACK);
                        meText.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        hpText.setTextColor(Color.BLACK);
                        filmText.setTextColor(Color.RED);
                        meText.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        meText.setTextColor(Color.RED);
                        filmText.setTextColor(Color.BLACK);
                        filmText.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new FilmFragment());
        list.add(new MeFragment());
        adapter = new FraPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  // 初始化显示第一个页面
    }
}
