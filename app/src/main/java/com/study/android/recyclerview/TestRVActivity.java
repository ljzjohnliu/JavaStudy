package com.study.android.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestRVActivity extends BaseSimpleActivity {
    private static final String TAG = "TestRecycler";

    private Context mContext;
    private RecyclerAdapter recyclerAdapter;
    private StaggeredRecyclerAdapter staggeredRecyclerAdapter;
    private List<String> list = new ArrayList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @OnClick({R.id.opt_add, R.id.opt_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.opt_add:
                staggeredRecyclerAdapter.addData(1);
                break;
            case R.id.opt_delete:
                staggeredRecyclerAdapter.removeData(1);
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler);
        ButterKnife.bind(this);
        mContext = this;
        Log.d(TAG, "------onCreate: ");
        initData();
        recyclerAdapter = new RecyclerAdapter(this, list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));//默认是竖向布局的
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));//线性管理器，支持横向、纵向、正序、倒序
//        recyclerView.setLayoutManager(new GridLayoutManager(GridLayoutManager.this, 2));//网格式布局

        DividerItemDecoration horizontalDiv = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        horizontalDiv.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.fen_line_divider));
//        horizontalDiv.setDrawable(new ColorDrawable(ContextCompat.getColor(mContext, R.color.color_fen)));//该写法不生！
//        horizontalDiv.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.ssss));
        DividerItemDecoration verticalDiv = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        verticalDiv.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.green_line_divider));
        recyclerView.addItemDecoration(horizontalDiv);
        recyclerView.addItemDecoration(verticalDiv);

//        recyclerView.setAdapter(recyclerAdapter);

        /* StaggeredGridLayoutManager 瀑布就式布局管理器 */
        staggeredRecyclerAdapter = new StaggeredRecyclerAdapter(this, list);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(staggeredRecyclerAdapter);
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            list.add("item " + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                staggeredRecyclerAdapter.addData(1);
                break;
            case R.id.action_delete:
                staggeredRecyclerAdapter.removeData(1);
                break;
        }

        return super.onOptionsItemSelected(item);
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
}