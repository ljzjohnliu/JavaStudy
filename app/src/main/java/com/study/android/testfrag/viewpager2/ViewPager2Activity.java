package com.study.android.testfrag.viewpager2;

import static com.study.android.testfrag.viewpager2.TestResultActivity.RESULT_FROM_TEST;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.fragment.FilmFragment;
import com.study.android.fragment.GameFragment;
import com.study.android.fragment.HomeFragment;
import com.study.android.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewPager2Activity extends BaseSimpleActivity {
    private List<Fragment> list;
    private MyFraStatePagerAdapter adapter;

    @BindView(R.id.myViewPager)
    ViewPager2 mViewPager2;
    @BindView(R.id.hp_text)
    TextView hpText;
    @BindView(R.id.film_text)
    TextView filmText;
    @BindView(R.id.game_text)
    TextView gameText;
    @BindView(R.id.me_text)
    TextView meText;

    @OnClick({R.id.hp_text, R.id.film_text, R.id.game_text, R.id.me_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hp_text:
                mViewPager2.setCurrentItem(0);
                break;
            case R.id.film_text:
                mViewPager2.setCurrentItem(1);
                break;
            case R.id.game_text:
                mViewPager2.setCurrentItem(2);
                break;
            case R.id.me_text:
                mViewPager2.setCurrentItem(3);
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2_layout);
        ButterKnife.bind(this);
        initViewPager();
    }


    private void initViewPager() {
        //??????????????????
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mViewPager2.setCurrentItem(position);
                Log.d("TAG", "onPageSelected: position = " + position);
                switch (position) {
                    case 0:
                        hpText.setTextColor(Color.RED);
                        filmText.setTextColor(Color.BLACK);
                        gameText.setTextColor(Color.BLACK);
                        meText.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        hpText.setTextColor(Color.BLACK);
                        filmText.setTextColor(Color.RED);
                        gameText.setTextColor(Color.BLACK);
                        meText.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        hpText.setTextColor(Color.BLACK);
                        filmText.setTextColor(Color.BLACK);
                        gameText.setTextColor(Color.RED);
                        meText.setTextColor(Color.BLACK);
                        break;
                    case 3:
                        hpText.setTextColor(Color.BLACK);
                        filmText.setTextColor(Color.BLACK);
                        gameText.setTextColor(Color.BLACK);
                        meText.setTextColor(Color.RED);
                        break;
                }
            }
        });

        // ???Fragment?????????List????????????
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new FilmFragment());
        list.add(new GameFragment());
        list.add(new MeFragment());
        adapter = new MyFraStatePagerAdapter(this, list);
        mViewPager2.setAdapter(adapter);
        mViewPager2.setOffscreenPageLimit(1);
        mViewPager2.setCurrentItem(0);  // ??????????????????????????????
    }

    /**
     * ????????????fragment???????????????startActivityForResult()????????????????????????startActivityForResult()?????????????????????????????????
     * ??????Fragment???????????????getActivity().startActivityForResult()???Fragment???Activity????????????onActivityResult?????????Fragment???????????????result?????????Activity????????????
     * requestCode????????????OnActivityResult()?????????requestCode???????????????startActivityForResult(intent, requestCode)???????????????requestCode???????????????
     * ????????????getActivity().startActivityForResult()?????????Fragment???????????????onActivityResult?????????
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String backMsg = data.getStringExtra(RESULT_FROM_TEST);
        Log.i("ViewPager2Ac", "activity???onActivityResult requestCode=" + requestCode
                + ", resultCode" + resultCode + ", backMsg = " + backMsg);
    }
}
