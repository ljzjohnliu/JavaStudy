package com.study.android.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.study.android.R;
import com.study.android.base.BaseSimpleActivity;
import com.study.android.utils.DisplayUtil;
import com.study.android.utils.SystemUIUtils;
import com.study.android.utils.Utils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestBitmapActivity extends BaseSimpleActivity {
    private static final String TAG = "TestBitmapAc";

    private Context mContext;
    private AnimationDrawable animationDrawable1;

    @BindView(R.id.anim_img1)
    ImageView animImg1;
    @BindView(R.id.anim_img2)
    ImageView animImg2;
    @BindView(R.id.anim_img3)
    ImageView animImg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bitmap);
        ButterKnife.bind(this);
        mContext = this;
        Log.d(TAG, "onCreate, Display density = " + DisplayUtil.getDisplayDensity(this)
                + ", screen width = " + SystemUIUtils.getScreenWidth(this)
                + ", screen height = " + SystemUIUtils.getScreenHeight(this));
        Log.d(TAG, "onCreate: ScreenDensities = " + Arrays.toString(SystemUIUtils.getScreenDensities(this)));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen);
        Log.d(TAG, "onCreate: bitmap size = " + bitmap.getByteCount() / 1024);//KB

        Bitmap hBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen_h);
        Log.d(TAG, "onCreate: hBitmap size = " + hBitmap.getByteCount() / 1024);//KB

        Bitmap xxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen_xx);
        Log.d(TAG, "onCreate: xxBitmap size = " + xxBitmap.getByteCount() / 1024);//KB

        Bitmap xxxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.queen_xxx);
        Log.d(TAG, "onCreate: xxxBitmap size = " + xxxBitmap.getByteCount() / 1024);//KB
    }
}
