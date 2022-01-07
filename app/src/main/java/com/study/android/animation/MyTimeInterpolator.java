package com.study.android.animation;

import android.animation.TimeInterpolator;
import android.util.Log;

public class MyTimeInterpolator implements TimeInterpolator {

    // 因子数值越小振动频率越高
    private float factor;

    public MyTimeInterpolator() {
        this.factor = 0.15f;
    }

    @Override
    public float getInterpolation(float input) {
//        Log.d("xxx", "getInterpolation: input = " + input + ", after = " + (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1));
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}