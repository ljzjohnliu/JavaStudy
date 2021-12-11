package com.study.android.lifecycle;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.study.android.R;
import com.study.android.communicate.ProcessComActivity;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityA extends AppCompatActivity {
    private static final String TAG = "AAAAAA";

    SeriesDataset mDataset;

    @OnClick({R.id.start_activity_b, R.id.start_dialog_activity})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.start_activity_b:
                intent.setClass(this, ActivityB.class);
                break;
            case R.id.start_dialog_activity:
                intent.setClass(this, ActivityC.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "------onCreate: ");
        setContentView(R.layout.activity_a);
        ButterKnife.bind(this);
        mDataset = new SeriesDataset();
        mDataset.setName("onCreate");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "------onStart: ");
        mDataset.setName("onStart");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "------onResume: ");
        mDataset.setName("onResume");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "------onPause: ");
        mDataset.setName("onPause");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "------onStop: ");
        mDataset.setName("onStop");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.STOP);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "------onRestart: ");
        mDataset.setName("onRestart");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.RESTART);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "------onDestroy: ");
        mDataset.setName("onDestroy");
        mDataset.setState(SeriesDataset.LIFE_CYCLE.DESTROY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the current data, for instance when changing screen orientation
        outState.putSerializable("dataset", mDataset);
        Log.d(TAG, "------onSaveInstanceState: outState = " + outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        // restore the current data, for instance when changing the screen
        // orientation
        mDataset = (SeriesDataset) savedState.getSerializable("dataset");
        Log.d(TAG, "------onRestoreInstanceState: savedState = " + savedState);
    }

    public static class SeriesDataset implements Serializable {
        public enum LIFE_CYCLE {
            CREATE, START, RESTART, RESUME, PAUSE, STOP, DESTROY
        }

        private String name;
        private LIFE_CYCLE state;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LIFE_CYCLE getState() {
            return state;
        }

        public void setState(LIFE_CYCLE state) {
            this.state = state;
        }
    }
}