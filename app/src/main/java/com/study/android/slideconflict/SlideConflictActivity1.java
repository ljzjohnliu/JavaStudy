package com.study.android.slideconflict;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 内外滑动方向不一致时处理思路
 * 这一类场景其实比较容易分析，因为外层和内层滑动的方向不一致，所以根据手势的动向来确定把事件给谁。
 * 默认情况下，当点击内层控件时，事件会先一层层从外层传到内层，由内层来处理。这里以外层为左右滑动，
 * 内层为上下滑动为例。当判定手势的滑动为左右时，需要外层来消费事件，所以外层将事件拦截，
 * 即在外层的onInterceptTouchEvent中检测为ACTION_MOVE时返回true；而如果判定手势的滑动为上下时，
 * 需要内层来消费事件，外层不需要拦截，事件会传递到内层来处理（具体的代码实现，在后面会详细列出）。
 * 这样就通过判断滑动的方向来决定事件的处理对象，从而解决滑动冲突的问题。
 * 那么，如何来判定手势的滑动方向呢？最常用的办法就是比较水平和竖直方向上的位移值来判断。
 * MotionEvent事件包含了事件的坐标，只要记录一次移动事件的起点和终点坐标，如下图所示，
 * 通过比较在水平方向的位移|dx|和|dy|的大小，来决定滑动的方向：|dy|>|dx|，本次移动的方向认为是竖直方向；
 * 反之，则认为是水平方向。当然，还可以通过夹角α的大小、斜率、速率等方式来作为判断条件。
 */
public class SlideConflictActivity1 extends BaseSimpleActivity {
    private static final String TAG = "TestBitmapAc";

    private RecyclerAdapter recyclerAdapter;
    private List<String> list = new ArrayList<>();

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//线性管理器，支持横向、纵向、正序、倒序
//        recyclerView.setLayoutManager(new GridLayoutManager(GridLayoutManager.this, 2));//网格式布局
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            list.add("item " + i);
        }
    }

    public static class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Context context;
        private List<String> mDatas;


        public RecyclerAdapter(Context context, List<String> mDatas) {
            super();
            this.context = context;
            this.mDatas = mDatas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_slide_conflict, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.pos);
        }
    }
}
