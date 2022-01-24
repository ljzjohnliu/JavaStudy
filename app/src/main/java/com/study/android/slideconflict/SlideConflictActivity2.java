package com.study.android.slideconflict;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.recyclerview.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 内外滑动方向一致时处理思路
 * 这种场景因为滑动方向一致，所以无法通过上述的方式来判断将事件交给谁处理。在这种情况下，往往需要根据业务的需要来判定谁来处理事件。
 * 比如竖直方向的ScrollView嵌套ListView的场景下，手指在ListView上上下滑动时：当ListView滑动到顶部且手势向下时，
 * 显然ListView不能再向下滑动了，这种情况下事件需要被外层控件拦截，由ScrollView来消费；当ListView滑动到底部且手势向上时，
 * 显然ListView也不能再向上滑动了，这种情况下事件也需要被外层控件拦截，由ScrollView来消费；其它情况下，ScrollView就不能再拦截了，
 * 滑动事件就需要由ListView来消费了，即此时上下滑动时，滑动的是ListView，而不是ScrollView。后面会以这为案例进行编码实现。
 *
 * 需要注意的是RecyclerView implements ScrollingView, NestedScrollingChild2, NestedScrollingChild3
 * 它跟ScrollView 嵌套使用的时候，默认就是如上的行为，在RecyclerView有内容可滑动的时候是RecyclerView消费事件，如果到头或者到底了，就交由ScrollView消费事件。
 */
public class SlideConflictActivity2 extends BaseSimpleActivity {
    private static final String TAG = "TestBitmapAc";

    private RecyclerAdapter recyclerAdapter;
    private List<String> list = new ArrayList<>();

    ListView listView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_conflict1);
        ButterKnife.bind(this);

        initData();
        recyclerAdapter = new RecyclerAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//默认是竖向布局的
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));//线性管理器，支持横向、纵向、正序、倒序
//        recyclerView.setLayoutManager(new GridLayoutManager(GridLayoutManager.this, 2));//网格式布局
        recyclerView.setAdapter(recyclerAdapter);
//        recyclerAdapter.notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            list.add("item " + i);
        }
    }
}
